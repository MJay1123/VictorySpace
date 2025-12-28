<template>
  <div class="vote-detail-view">
    <div class="content-wrapper">
      <VoteSection :vote-id="Number(voteId)" />
      <CommentSection :vote-id="Number(voteId)" />
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import VoteSection from '../components/vote/VoteSection.vue';
import CommentSection from '../components/comment/CommentSection.vue';

const route = useRoute()
const voteId = route.params.id
</script>

<style scoped>
.vote-detail-view {
  display: flex;
  justify-content: center;
  width: 100%;
  min-height: 100vh;
  background: #f9fafb;
}

.content-wrapper {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 0 20px;
}
</style>
