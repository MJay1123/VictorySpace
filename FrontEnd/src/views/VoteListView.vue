<template>
  <div>
    <h1>투표 목록</h1>
    <ul>
      <li v-for="vote in votes" :key="vote.id" @click="openVote(vote.id)">
        <h3>{{ vote.title }}</h3>
        <p>{{ vote.content }}</p>
      </li>
    </ul>

    <!-- 상세 모달 -->
    <VoteDetailModal
      v-if="selectedVoteId !== null"
      :vote-id="selectedVoteId"
      @close="selectedVoteId = null"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import VoteDetailModal from '@/components/modals/VoteDetailModal.vue';

const votes = ref([]);
const selectedVoteId = ref(null);

// 투표 목록 불러오기
onMounted(async () => {
  try {
    const res = await fetch('http://localhost:8080/api/vote');
    if (!res.ok) throw new Error('투표 목록 불러오기 실패');
    votes.value = await res.json();
  } catch (error) {
    console.error(error);
  }
});

function openVote(id) {
  selectedVoteId.value = id;
}
</script>

<style scoped>
li {
  cursor: pointer;
  border-bottom: 1px solid #ccc;
  padding: 10px 0;
}
</style>
