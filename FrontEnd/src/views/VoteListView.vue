<template>
  <div>
    <h1>투표 목록</h1>
    <button @click="openCreateVoteModal">새 투표 만들기</button>

    <ul>
      <li v-for="vote in votes" :key="vote.id" @click="openVoteDetailModal(vote.id)">
        <h3>{{ vote.title }}</h3>
        <p>{{ vote.content }}</p>
      </li>
    </ul>

    <!-- 상세 모달 -->
    <VoteDetailModal v-if="selectedVoteId !== null" :vote-id="selectedVoteId" @close="closeVoteDetailModal" />

    <!-- 새 투표 생성 모달 -->
    <CreateVoteModal v-if="showCreateVoteModal" @close="closeCreateVoteModal" @created="onVoteCreated" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import voteApi from '@/api/voteApi.js';
import VoteDetailModal from '@/components/modals/VoteDetailModal.vue';
import CreateVoteModal from '@/components/modals/CreateVoteModal.vue';

const votes = ref([]);
const selectedVoteId = ref(null);

const showCreateVoteModal = ref(false)
const openCreateVoteModal = () => {showCreateVoteModal.value = true;};
const closeCreateVoteModal = () => {showCreateVoteModal.value = false;};

const onVoteCreated = () => {
  closeCreateVoteModal();
  fetchVotes();
};

// --- 투표 목록 가져오기 (axios 적용) ---
const fetchVotes = async () => {
  try {
    const res = await voteApi.findAll();
    votes.value = res.data;
  } catch (err) {
    console.error("투표 목록 불러오기 실패:", err);
  }
};

// 투표 목록 불러오기
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
li {
  cursor: pointer;
  border-bottom: 1px solid #ccc;
  padding: 10px 0;
}
</style>
