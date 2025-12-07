<template>
  <div class="vote-container">
    <h1 class="page-title">⚡ Vote List</h1>
    <button @click="openCreateVoteModal" class="create-btn">+ 새 투표 만들기</button>

    <div v-for="vote in votes" :key="vote.id" class="vote-card" @click="openVoteDetailModal(vote.id)">

      <div class="left">
        <h3 class="title">{{ vote.title }}</h3>
        <p class="content">{{ vote.content }}</p>
      </div>

      <div class="right">
        <p class="nickname">작성자 : <b>{{ vote.nickname ?? '불러오는 중...' }}</b></p>
        <p class="date">{{ formatDate(vote.createdAt) }}</p>
      </div>
    </div>

    <VoteDetailModal v-if="selectedVoteId !== null" :vote-id="selectedVoteId" @close="closeVoteDetailModal" />

    <CreateVoteModal v-if="showCreateVoteModal" @close="closeCreateVoteModal" @created="onVoteCreated" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import voteApi from '@/api/voteApi.js';
import memberApi from '../api/memberApi';
import VoteDetailModal from '@/components/modals/VoteDetailModal.vue';
import CreateVoteModal from '@/components/modals/CreateVoteModal.vue';

const votes = ref([]);
const selectedVoteId = ref(null);

const showCreateVoteModal = ref(false)
const openCreateVoteModal = () => { showCreateVoteModal.value = true; };
const closeCreateVoteModal = () => { showCreateVoteModal.value = false; };

const onVoteCreated = () => {
  closeCreateVoteModal();
  fetchVotes();
};

const formatDate = (dateString) => {
  if (!dateString) return '';

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

const fetchVotes = async () => {
  try {
    const res = await voteApi.findAll();
    const voteList = res.data;

    // 🔥 memberId 기반 닉네임 조회
    for (const vote of voteList) {
      if (vote.memberId) {
        try {
          const memberRes = await memberApi.findById(vote.memberId);
          vote.nickname = memberRes.data.nickname;
        } catch {
          vote.nickname = "알 수 없음";
        }
      }
    }

    votes.value = voteList;
  } catch (err) {
    console.error("투표 목록 불러오기 실패:", err);
  }
};

onMounted(() => {
  fetchVotes();
});

const showVoteDetailModal = ref(false)
const openVoteDetailModal = (id) => {
  showVoteDetailModal.value = true;
  selectedVoteId.value = id;
};
const closeVoteDetailModal = () => {
  showVoteDetailModal.value = false;
  selectedVoteId.value = null;
};
</script>

<style scoped>
.vote-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.page-title {
  font-size: 28px;
  margin-bottom: 20px;
  font-weight: bold;
}

.create-btn {
  background: #2563eb;
  color: white;
  padding: 10px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 20px;
}

.vote-card {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background: #ffffff;
  border-radius: 12px;
  padding: 18px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: 0.2s;
  border: 1px solid #e3e3e3;
}

.vote-card:hover {
  transform: scale(1.02);
  background: #f8faff;
}

.title {
  font-weight: bold;
  font-size: 18px;
  margin: 0 0 4px;
}

.content {
  font-size: 14px;
  color: #555;
}

.right {
  text-align: right;
}

.nickname {
  font-size: 14px;
  color: #333;
}

.date {
  font-size: 12px;
  color: #777;
}
</style>