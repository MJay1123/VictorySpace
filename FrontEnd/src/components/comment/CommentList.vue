<template>
    <div class="comment-list">
        <CommentItem v-for="comment in comments" :key="comment.id" :comment="comment" />

        <p v-if="comments.length === 0" class="empty">
            아직 댓글이 없습니다.
        </p>
    </div>
</template>

<script setup>
import CommentItem from './CommentItem.vue'

defineProps({
    comments: {
        type: Array,
        default: () => [],
    },
})
</script>

<style scoped>
.empty {
    color: #777;
    font-size: 14px;
    margin-top: 10px;
}
</style>