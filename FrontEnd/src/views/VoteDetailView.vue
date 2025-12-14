<template>
  <div class="vote-detail-view">
    <VoteSection :vote-id="Number(voteId)" />
    <CommentSection :vote-id="Number(voteId)" />
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import VoteSection from '../components/vote/VoteSection.vue';
import CommentSection from '../components/comment/CommentSection.vue';

const route = useRoute()
const voteId = route.params.id
</script>
