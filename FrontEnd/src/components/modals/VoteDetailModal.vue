<template>
    <div class="modal-overlay">
        <div class="modal">
            <button class="close-btn" @click="$emit('close')">X</button>

            <h2>{{ vote.title }}</h2>
            <h3 v-if="category">{{ category.name }}</h3>

            <div class="competition">
                <!-- Home 영역 -->
                <div class="side home">
                    <h3>Home</h3>
                    <p>{{ vote.content }}</p>
                    <p>{{ homeCount }}표</p>
                    <label>
                        <input type="radio" value="home" v-model="selectedOption" /> 선택
                    </label>
                </div>

                <!-- Away 영역 -->
                <div class="side away" :class="{ gray: !vote.challengerId, red: vote.challengerId }">
                    <h3>Away</h3>
                    <p>{{ vote.challengerContent || '아직 도전자가 없습니다' }}</p>
                    <p>{{ awayCount }}표</p>
                    <label v-if="vote.challengerId">
                        <input type="radio" value="away" v-model="selectedOption" /> 선택
                    </label>
                    <button v-if="!vote.challengerId" @click="challengeVote">도전하기</button>
                </div>

                <!-- Neutral 영역 -->
                <div class="side neutral">
                    <h3>Neutral</h3>
                    <p>{{ neutralCount }}표</p>
                    <label>
                        <input type="radio" value="neutral" v-model="selectedOption" /> 선택
                    </label>
                </div>
            </div>

            <!-- 투표 / 취소 버튼 -->
            <div class="vote-actions">
                <button v-if="!userVote" @click="voteChoice">투표</button>
                <button v-else @click="cancelVote">투표취소</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import voteApi from '../../api/voteApi';
import voterApi from '../../api/voterApi';
import { categoryApi } from '../../api/categoryApi';

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

const user = JSON.parse(localStorage.getItem('userInfo'))

// 👉 1) 투표 여부 확인 API
async function fetchUserVote() {
    try {
        if (!user || !user.id) {
            userVote.value = null;
            return;
        }

        const res = await voterApi.getVoterByVoteAndMemberId(props.voteId, user.id);

        if (res.status === 404) {
            userVote.value = null;
            return;
        }

        // body가 비어있지 않을 때만 파싱
        const text = await res.data;
        userVote.value = text ? JSON.parse(text) : null;

    } catch (err) {
        console.error("투표 여부 확인 실패", err);
        userVote.value = null; // 안전하게 초기화
    }
}

// 👉 2) 투표 정보 GET
async function fetchVoteDetail(id) {
    try {
        const res = await voteApi.getVoteById(id);
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
        const res = await categoryApi.getAllCategories();

        const categories = res.data; // 전체 배열

        category.value = categories.find(c => c.id === categoryId) || null; // 일치하는 것 하나만
    } catch (err) {
        console.error(err);
        category.value = null;
    }
}

// 👉 3) 전체 투표자 GET
async function fetchVoters(voteId) {
    const res = await voterApi.getVotersByVoteId(voteId);
    voters.value = await res.data;

    homeCount.value = voters.value.filter(v => v.content === 'home').length
    awayCount.value = voters.value.filter(v => v.content === 'away').length
    neutralCount.value = voters.value.filter(v => v.content === 'neutral').length
}


// 👉 4) 투표 등록
const voteChoice = async (side) => {
    if (!user || !user.id) {
        alert("로그인이 필요합니다.");
        return;
    }

    const res = await fetch('http://localhost:8080/api/voter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            voteId: props.voteId,
            memberId: user.id,
            content: selectedOption.value
        })
    });

    if (!res.ok) {
        const msg = await res.text();
        console.error("서버 오류:", msg);
        throw new Error("투표 실패 ❌");
    }

    await refreshData();
};

// 👉 5) 투표 취소
async function cancelVote() {
    if (!userVote.value) return;

    const res = await fetch(`http://localhost:8080/api/voter/${userVote.value.id}`, {
        method: 'DELETE'
    });

    if (!res.ok) return alert("삭제 실패!");

    await refreshData();
}

// 공통 새로고침
const refreshData = async () => {
    await fetchVoteDetail(props.voteId);  // vote 받아오기
    await fetchCategory(vote.value.categoryId); // categoryId 존재할 때만
    await fetchVoters(props.voteId);
    await fetchUserVote();
};

// voteId 변경 시 자동 reload
watch(() => props.voteId, () => refreshData(), { immediate: true });

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

        const res = await fetch(`http://localhost:8080/api/vote/challenge/${vote.value.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                challengerId: user.id,
                challengerContent: inputContent
            })
        });

        if (!res.ok) throw new Error('도전 요청 실패');

        await fetchVoteDetail(props.voteId); // 상세 정보 갱신
    } catch (error) {
        console.error(error);
    }
};
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal {
    background: white;
    padding: 20px;
    border-radius: 8px;
    width: 600px;
}

.close-btn {
    float: right;
    background: red;
    color: white;
    border: none;
    cursor: pointer;
    padding: 5px 10px;
}

.competition {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.vote-options label {
    display: block;
    margin-bottom: 10px;
    cursor: pointer;
}

.vote-actions button {
    margin-top: 10px;
    padding: 5px 10px;
    cursor: pointer;
}

.side {
    width: 45%;
    padding: 10px;
    border-radius: 8px;
    text-align: center;
}

.home {
    background-color: #cce4ff;
    /* 파랑 */
}

.away.gray {
    background-color: #e0e0e0;
    /* 회색 */
}

.away.red {
    background-color: #ffcccc;
    /* 빨강 */
}

button {
    margin-top: 10px;
    padding: 5px 10px;
    cursor: pointer;
}
</style>