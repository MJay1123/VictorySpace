<template>
    <div class="modal-overlay">
        <div class="modal-card">

            <!-- Close -->
            <button class="close-btn" @click="$emit('close')">✕</button>

            <!-- Title -->
            <h2 class="modal-title">{{ vote.title }}</h2>
            <p class="category-badge" v-if="category">{{ category.name }}</p>

            <!-- Creator & Challenger Info -->
            <div class="vote-meta">
                <span>📌 생성자: <strong>{{ voteOwnerNickname }}</strong></span>
                <span v-if="challengerNickname">⚔️ 도전자: <strong>{{ challengerNickname }}</strong></span>
            </div>

            <!-- Vote UI -->
            <div class="competition">

                <!-- HOME -->
                <div class="side home" :class="{ selected: userVote?.content === 'home' }">
                    <h3>Home</h3>
                    <p>{{ vote.content }}</p>
                    <p class="count">{{ homeCount }}표</p>

                    <label>
                        <input type="radio" value="home" v-model="selectedOption" :disabled="Boolean(userVote)" />
                        선택
                    </label>
                </div>

                <!-- AWAY -->
                <div class="side away"
                    :class="{ gray: !vote.challengerId, red: vote.challengerId, selected: userVote?.content === 'away' }">
                    <h3>Away</h3>
                    <p>{{ vote.challengerContent || '도전자가 없습니다.' }}</p>
                    <p class="count">{{ awayCount }}표</p>

                    <label v-if="vote.challengerId">
                        <input type="radio" value="away" v-model="selectedOption" :disabled="Boolean(userVote)" />
                        선택
                    </label>

                    <button v-if="!vote.challengerId" class="challenge-btn" @click="challengeVote">도전하기</button>
                </div>

                <!-- NEUTRAL -->
                <div class="side neutral" :class="{ selected: userVote?.content === 'neutral' }">
                    <h3>Neutral</h3>
                    <p class="count">{{ neutralCount }}표</p>

                    <label>
                        <input type="radio" value="neutral" v-model="selectedOption" :disabled="Boolean(userVote)" />
                        선택
                    </label>
                </div>
            </div>

            <!-- Action Button -->
            <button class="vote-btn" :disabled="!selectedOption" @click="userVote ? cancelVote() : voteChoice()">
                {{ userVote ? "투표 취소" : "투표하기" }}
            </button>

            <!-- Comments -->
            <div class="comments-section">
                <h3 class="comment-title">💬 댓글</h3>

                <div class="write-comment">
                    <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
                    <button @click="submitComment" :disabled="!newComment.trim()">작성</button>
                </div>

                <div v-if="comments.length === 0" class="no-comment">아직 댓글이 없습니다 😁</div>

                <div class="comment-list">
                    <div class="comment-item" v-for="comment in comments" :key="comment.id">
                        <div class="comment-left">👤 {{ comment.nickname }}</div>
                        <div class="comment-right">

                            <!-- 수정 중일 때 -->
                            <div v-if="editingCommentId === comment.id">
                                <textarea v-model="editContent"></textarea>
                                <button class="save-btn" @click="saveEdit(comment.id)">저장</button>
                                <button class="cancel-btn" @click="cancelEdit">취소</button>
                            </div>

                            <!-- 일반 표시 -->
                            <div v-else>
                                <!-- 🔥 삭제된 경우 -->
                                <span v-if="comment.deletedAt !== null" class="deleted-text">
                                    삭제된 댓글입니다.
                                </span>

                                <!-- 🔥 정상 댓글 -->
                                <span v-else>
                                    {{ comment.content }}
                                </span>
                                <div class="comment-meta">{{ formatDate(comment.createdAt) }}</div>

                                <!-- 작성자만 수정/삭제 가능 -->
                                <div class="comment-actions"
                                    v-if="comment.memberId === userId && comment.deletedAt === null">
                                    <button class="edit-btn" @click="startEdit(comment)">수정</button>
                                    <button class="delete-btn" @click="deleteComment(comment.id)">삭제</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>


<script setup>
import { ref, watch, onMounted } from 'vue';
import voteApi from '../../api/voteApi';
import voterApi from '../../api/voterApi';
import categoryApi from '../../api/categoryApi';
import commentApi from '../../api/commentApi';
import memberApi from '../../api/memberApi';

const props = defineProps({
    voteId: Number,
});

const vote = ref({})
const voteOwnerNickname = ref("");
const challengerNickname = ref("");
const voters = ref([])
const userVote = ref(null)
const selectedOption = ref('home') // 기본 선택값
const homeCount = ref(0)
const awayCount = ref(0)
const neutralCount = ref(0)
const category = ref(null)
const comments = ref([])
const newComment = ref("");
const editingCommentId = ref(null);
const editContent = ref("");
const userId = ref(null);


const user = JSON.parse(localStorage.getItem('userInfo'))

// 날짜 formatting
const formatDate = (dateString) => {
    if (!dateString) return "";

    const date = new Date(dateString);

    const year = String(date.getFullYear()).slice(2);
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const period = hours >= 12 ? "오후" : "오전";

    if (hours > 12) hours -= 12;

    return `${year}.${month}.${day} ${period} ${hours}:${minutes}`;
};


// 로그인 정보 가져오기
async function fetchUserId() {
    if (!user) return;
    const res = await memberApi.findByEmail(user.email);
    userId.value = res.data.id;
}


// 👉 1) 투표 여부 확인 API
async function fetchUserVote() {
    try {
        if (!user) {
            userVote.value = null;
            return;
        }

        try {
            const res = await voterApi.findByVoteAndMemberId(props.voteId, userId.value);
            userVote.value = res?.data ?? null;
        } catch (err) {
            userVote.value = null;
            console.error(err);
        }

    } catch (err) {
        console.error("투표 여부 확인 실패", err);
        userVote.value = null; // 안전하게 초기화
    }
}

// 👉 2) 투표 정보 GET
async function fetchVoteDetail(id) {
    try {
        const res = await voteApi.findById(id);
        vote.value = res.data;

        if (vote.value?.categoryId) {
            await fetchCategory(vote.value.categoryId);
        }

    } catch (err) {
        console.error("투표 상세 불러오기 실패:", err);
    }
}

// 카테고리 정보 GET
async function fetchCategory(categoryId) {
    try {
        const res = await categoryApi.findAll();

        const categories = res.data; // 전체 배열

        category.value = categories.find(c => c.id === categoryId) || null; // 일치하는 것 하나만
    } catch (err) {
        console.error(err);
        category.value = null;
    }
}

async function fetchVoteOwnerInfo() {
    const res = await memberApi.findById(vote.value.memberId);
    voteOwnerNickname.value = res.data.nickname;
}

async function fetchChallengerInfo() {
    const res = await memberApi.findById(vote.value.challengerId);
    challengerNickname.value = res.data.nickname;
}

// 👉 3) 전체 투표자 GET
async function fetchVoters(voteId) {
    try {
        const res = await voterApi.findByVoteId(voteId);
        voters.value = res.data;

        homeCount.value = voters.value.filter(v => v.content === 'home').length
        awayCount.value = voters.value.filter(v => v.content === 'away').length
        neutralCount.value = voters.value.filter(v => v.content === 'neutral').length
    } catch (err) {
        console.error("투표자 조회 실패:", err);
        voters.value = [];
        homeCount.value = awayCount.value = neutralCount.value = 0;
    }
}

// 👉 4) 투표 등록
const voteChoice = async () => {
    if (!selectedOption.value) return alert("옵션을 선택해야 합니다!");

    if (!user) {
        alert("로그인이 필요합니다.");
        return;
    }
    try {
        await voterApi.createVoter({
            voteId: props.voteId,
            memberId: userId.value,
            content: selectedOption.value
        });

        await refreshData();

    } catch (err) {
        console.error("투표 실패:", err);
        alert("투표 중 오류가 발생했습니다.");
    }
}

// 👉 5) 투표 취소

async function cancelVote() {
    if (!userVote.value) return;

    try {
        await voterApi.deleteVoter(userVote.value.id);
        await refreshData();
    } catch (e) {
        alert("삭제 실패!");
        console.error(e);
    }
}

// 도전하기 버튼 클릭
const challengeVote = async () => {
    try {
        // 로그인 사용자 정보
        const user = JSON.parse(localStorage.getItem('userInfo'));
        if (!user) {
            alert('로그인이 필요합니다.');
            return;
        }

        const inputContent = prompt('도전 내용을 입력하세요');
        if (!inputContent) return;

        const voteDTO = {
            challengerId: userId.value,
            challengerContent: inputContent
        }
        const res = await voteApi.challengeVote(vote.value.id, voteDTO)

        await refreshData();
        alert('도전 완료!');
    } catch (error) {
        console.error("도전하기 오류:", error);
        alert("도전 요청 중 오류가 발생했습니다.");
    }
};

async function fetchComments() {
    if (!vote.value?.id) return;
    try {
        const res = await commentApi.findByVoteId(vote.value.id);
        const list = res.data;

        // 🔥 memberId → nickname 변환
        for (const comment of list) {
            try {
                const memberDTO = await memberApi.findById(comment.memberId);
                comment.nickname = memberDTO.data.nickname;
            } catch {
                comment.nickname = "알 수 없음";
            }
        }
        comments.value = list;
    } catch (err) {
        console.error("댓글 조회 실패:", err);
    }
}

/** 📌 댓글 작성 */
const submitComment = async () => {
    if (!newComment.value.trim()) return;

    await commentApi.createComment({
        voteId: props.voteId,
        memberId: userId.value,
        content: newComment.value
    });

    newComment.value = "";
    await fetchComments(); // 🔥 작성 후 즉시 새로고침
};

/** 📌 댓글 수정 시작 */
const startEdit = (comment) => {
    editingCommentId.value = comment.id;
    editContent.value = comment.content;
};

/** 📌 수정 취소 */
const cancelEdit = () => {
    editingCommentId.value = null;
    editContent.value = "";
};

/** 📌 수정 저장 */
const saveEdit = async (id) => {
    if (!editContent.value.trim()) return;

    await commentApi.updateComment(id, { content: editContent.value });

    editingCommentId.value = null;
    editContent.value = "";

    await fetchComments();
};

/** 📌 댓글 삭제 */
const deleteComment = async (id) => {
    if (!confirm("삭제하시겠습니까?")) return;

    await commentApi.deleteComment(id);
    await fetchComments();
};

// 공통 새로고침
const refreshData = async () => {
    await fetchUserId();
    await fetchVoteDetail(props.voteId);
    await fetchVoteOwnerInfo();
    await fetchChallengerInfo();
    await fetchCategory(vote.value.categoryId);
    await fetchVoters(props.voteId);
    await fetchUserVote();
    await fetchComments();

};

// voteId 변경 시 자동 reload
watch(() => props.voteId, () => refreshData(), { immediate: true });

</script>

<style scoped>
.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.55);
    display: flex;
    align-items: center;
    justify-content: center;
    backdrop-filter: blur(2px);
    z-index: 1000;
}

.modal-card {
    background: #ffffff;
    padding: 40px;
    width: 1300px;
    max-height: 90vh;
    overflow-y: auto;
    border-radius: 20px;
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.25);
    animation: fadeIn .25s ease-out;
    position: relative;
}

.close-btn {
    position: absolute;
    top: 20px;
    right: 25px;
    background: none;
    border: none;
    font-size: 32px;
    cursor: pointer;
    color: #444;
    transition: 0.2s;
}

.close-btn:hover {
    color: #000;
}

.modal-title {
    text-align: center;
    font-weight: 700;
    font-size: 34px;
    margin-bottom: 14px;
    color: #222;
}

.category-badge {
    display: block;
    text-align: center;
    font-size: 18px;
    background: #d7e3ff;
    padding: 8px 14px;
    border-radius: 12px;
    width: fit-content;
    margin: 0 auto 25px auto;
}

.vote-meta {
    display: flex;
    justify-content: center;
    gap: 40px;
    font-size: 25px;
    margin-bottom: 25px;
    color: #333;
}

/* ===== 투표 UI ===== */

.competition {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    margin: 35px 0;
}

.side {
    flex: 1;
    padding: 25px;
    border-radius: 16px;
    background: #f6f7fb;
    text-align: center;
    font-size: 20px;
    transition: .3s;
    cursor: pointer;
    border: 2px solid transparent;
}

/* Home / Away / Neutral 기본 색 */
.side.home {
    background: #e3efff;
}

.side.away {
    background: #ffe4e4;
}

.side.neutral {
    background: #e4ffe6;
}

/* 선택 시 강조 */
.side.home.selected {
    border: 4px solid #2a65ff;
    background: #d8e4ff;
    transform: scale(1.05);
}

.side.away.selected {
    border: 4px solid #ff3b3b;
    background: #ffd6d6;
    transform: scale(1.05);
}

.side.neutral.selected {
    border: 4px solid #1bbf4b;
    background: #d8ffdf;
    transform: scale(1.05);
}

/* 라디오 버튼 크게 */
input[type="radio"] {
    transform: scale(1.8);
    margin-top: 10px;
    cursor: pointer;
}

.count {
    font-weight: bold;
    margin-top: 10px;
    font-size: 22px;
}

/* ===== 투표 버튼 ===== */
.vote-btn {
    width: 100%;
    background: #3b82f6;
    color: white;
    padding: 20px;
    border-radius: 15px;
    font-size: 22px;
    font-weight: bold;
    border: none;
    margin-top: 28px;
    cursor: pointer;
    transition: .25s;
}

.vote-btn:hover {
    background: #245ad8;
    transform: translateY(-3px);
}

/* ===== 댓글 ===== */

.comments-section {
    margin-top: 35px;
}

.comment-title {
    font-size: 26px;
    font-weight: 700;
    padding-bottom: 10px;
    border-bottom: 3px solid #eee;
    margin-bottom: 16px;
}

.no-comment {
    text-align: center;
    color: #777;
    font-style: italic;
    margin: 12px 0;
}

.comment-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
    margin-top: 8px;
}

.comment-item {
    display: flex;
    gap: 16px;
    align-items: flex-start;
    background: #f8f9fa;
    padding: 18px;
    border-radius: 12px;
    border: 1px solid #eef2f7;
    font-size: 18px;
}

.comment-left {
    width: 160px;
    font-weight: 700;
    color: #2b6be6;
    display: flex;
    align-items: center;
}

.comment-right {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.comment-content {
    background: white;
    padding: 12px;
    border-radius: 10px;
    box-shadow: 0 1px 0 rgba(0, 0, 0, 0.04);
    font-size: 18px;
    line-height: 1.45;
}

.comment-meta {
    font-size: 14px;
    color: #777;
}

/* Fade animation */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: scale(.95);
    }

    to {
        opacity: 1;
        transform: scale(1);
    }
}

.write-comment {
    margin-top: 20px;
    display: flex;
    gap: 10px;
}

.write-comment textarea {
    flex: 1;
    padding: 12px;
    font-size: 16px;
    border-radius: 10px;
    border: 2px solid #ccc;
}

.write-comment button {
    background: #3b82f6;
    color: white;
    padding: 14px 20px;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-size: 18px;
    font-weight: bold;
}

.comment-actions {
    margin-top: 8px;
    display: flex;
    gap: 12px;
}

.comment-actions button,
.save-btn,
.cancel-btn {
    background: #eee;
    padding: 6px 14px;
    border-radius: 8px;
    border: none;
    font-size: 15px;
    cursor: pointer;
}

.edit-btn {
    background: #ffe08a;
}

.delete-btn {
    background: #ff9f9f;
}

.save-btn {
    background: #6bdc7e;
    color: white;
}

.cancel-btn {
    background: #bbb;
    color: white;
}

.comment-item textarea {
    width: 100%;
    min-height: 60px;
    padding: 10px;
    border-radius: 10px;
    border: 2px solid #ccc;
}

.deleted-text {
    color: #aaa;
    font-style: italic;
    user-select: none;
}
</style>