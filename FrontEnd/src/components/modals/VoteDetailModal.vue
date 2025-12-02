<template>
    <div class="modal-overlay">
        <div class="modal">
            <button class="close-btn" @click="$emit('close')">X</button>

            <h2>{{ vote.title }}</h2>

            <div class="competition">
                <!-- Home 영역 -->
                <div class="side home">
                    <h3>Home</h3>
                    <p>{{ vote.content }}</p>
                    <p>{{ homeCount }}표</p>
                    <button @click="voteChoice('home')">투표</button>
                </div>

                <!-- Away 영역 -->
                <div class="side away" :class="{
                    'gray': !vote.challengerId,
                    'red': vote.challengerId
                }">
                    <h3>Away</h3>
                    <p>{{ vote.challengerContent || '아직 도전자가 없습니다' }}</p>
                    <p>{{ awayCount }}표</p>
                    <button v-if="!vote.challengerId" @click="challengeVote">도전하기</button>
                    <button v-else @click="voteChoice('away')">투표</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
    voteId: Number,
    memberId: Number // 로그인한 회원 id
});

const vote = ref({});
const voters = ref([]);
const userVote = ref(null); // 현재 로그인한 사용자의 투표 정보
const homeCount = ref(0);
const awayCount = ref(0);

async function fetchVoteDetail(id) {
    try {
        const res = await fetch(`http://localhost:8080/api/vote/${id}`);
        if (!res.ok) throw new Error('투표 상세 불러오기 실패');
        vote.value = await res.json();
    } catch (error) {
        console.error(error);
    }
}

async function fetchVoters(id) {
    try {
        const res = await fetch(`http://localhost:8080/api/voter/vote/${id}`);
        if (!res.ok) throw new Error('투표자 목록 불러오기 실패');
        voters.value = await res.json();

        // 로그인한 사용자의 투표가 있는지 확인
        const user = JSON.parse(localStorage.getItem('userInfo'));
        if (user) {
            userVote.value = data.find(v => v.memberId === user.id) || null;
        }

        homeCount.value = voters.value.filter(v => v.content === 'home').length;
        awayCount.value = voters.value.filter(v => v.content === 'away').length;
    } catch (error) {
        console.error(error);
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

// 투표하기
const voteChoice = async (side) => {
    const user = JSON.parse(localStorage.getItem('userInfo'));
    if (!user) {
        alert('로그인이 필요합니다.');
        return;
    }
    const res = await fetch('http://localhost:8080/api/voter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            voteId: props.voteId,
            memberId: user.id,
            content: side
        })
    });
    if (!res.ok) throw new Error('투표 실패');
    await fetchVoteDetail(props.voteId);
    await fetchVoters(props.voteId);
};

// 투표 취소
const cancelVote = async () => {
    if (!userVote.value) return;
    const res = await fetch(`http://localhost:8080/api/voter/${userVote.value.id}`, {
        method: 'DELETE'
    });
    if (!res.ok) throw new Error('투표 취소 실패');
    await fetchVoteDetail(props.voteId);
    await fetchVoters(props.voteId);
};

// voteId 바뀔 때마다 상세와 투표자 정보 fetch
watch(() => props.voteId, (id) => {
    if (id) {
        fetchVoteDetail(id);
        fetchVoters(id);
    }
}, { immediate: true });
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