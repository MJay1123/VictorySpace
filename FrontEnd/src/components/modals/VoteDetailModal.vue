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
            <h3 class="comment-title">💬 댓글</h3>

            <div v-if="comments.length === 0" class="no-comment">아직 댓글이 없습니다 😁</div>

            <div class="comment-list">
                <div class="comment-item" v-for="comment in comments" :key="comment.id">
                    <div class="comment-left">👤 {{ comment.memberNickname || `사용자 ${comment.memberId}` }}</div>
                    <div class="comment-right">{{ comment.content }}</div>
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
const voters = ref([])
const userVote = ref(null)
const selectedOption = ref('home') // 기본 선택값
const homeCount = ref(0)
const awayCount = ref(0)
const neutralCount = ref(0)
const category = ref(null)
const comments = ref([])
const voteOwnerNickname = ref("");
const challengerNickname = ref("");

const user = JSON.parse(localStorage.getItem('userInfo'))

// 👉 1) 투표 여부 확인 API
async function fetchUserVote() {
    try {
        if (!user) {
            userVote.value = null;
            return;
        }
        const memberDTO = await memberApi.findByEmail(user.email);
        const userId = memberDTO.data.id;

        try {
            const res = await voterApi.findByVoteAndMemberId(props.voteId, userId);
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

async function fetchComments() {
    if (!vote.value?.id) return;
    try {
        const res = await commentApi.findByVoteId(vote.value.id);
        comments.value = res.data;
    } catch (err) {
        console.error("댓글 조회 실패:", err);
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
        const memberDTO = await memberApi.findByEmail(user.email);
        const userId = memberDTO.data.id;

        await voterApi.createVoter({
            voteId: props.voteId,
            memberId: userId,
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

        const memberDTO = await memberApi.findByEmail(user.email);
        const userId = memberDTO.data.id;
        const voteDTO = {
            challengerId: userId,
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

// 공통 새로고침
const refreshData = async () => {
    await fetchVoteDetail(props.voteId);
    await fetchCategory(vote.value.categoryId);
    await fetchVoters(props.voteId);
    await fetchUserVote();
    await fetchComments();
    await fetchVoteOwnerInfo();
    await fetchChallengerInfo();
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
    /* 기존 대비 2배 */
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

.competition {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    margin: 35px 0;
}

/* ---- 기본 영역 스타일 ---- */
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

/* ---- Home / Away / Neutral 색깔 적용 ---- */
.side.home {
    background: #e3efff;
}

.side.away {
    background: #ffe4e4;
}

.side.neutral {
    background: #e4ffe6;
}

/* ---- 선택 조건: 더 진하게 강조 ---- */
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

/* ---- 라디오 버튼 확대 ---- */
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

/* 투표 버튼 */
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

/* 댓글 영역 */
.comment-title {
    margin-top: 35px;
    font-size: 26px;
    font-weight: bold;
    padding-bottom: 10px;
    border-bottom: 3px solid #ddd;
}

.comment-item {
    display: flex;
    gap: 15px;
    background: #f3f4f6;
    padding: 18px;
    border-radius: 12px;
    margin-top: 10px;
    font-size: 18px;
}

.comment-left {
    font-weight: bold;
    width: 150px;
    color: #333;
}

.comment-right {
    background: white;
    padding: 12px;
    border-radius: 10px;
    flex: 1;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>