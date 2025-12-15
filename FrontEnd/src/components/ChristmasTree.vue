<template>
  <div class="scene">
    <!-- ⭐ Star -->
    <div class="star">★</div>

    <!-- 🎄 Tree -->
    <div class="tree">
      <div class="layer l1"></div>
      <div class="layer l2"></div>
      <div class="layer l3"></div>
      <div class="layer l4"></div>

      <!-- ornaments -->
      <span v-for="(o, i) in ornaments" :key="i" class="ornament" :style="o" />

      <!-- lights -->
      <span v-for="(l, i) in lights" :key="'l' + i" class="light" :style="l" />
    </div>

    <!-- 🎆 Fireworks -->
    <div v-for="(f, i) in fireworks" :key="i" class="firework" :style="f.style">
      <span v-for="n in 12" :key="n" :style="{ transform: `rotate(${n * 30}deg)` }" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const ornaments = ref(
  Array.from({ length: 18 }).map(() => ({
    left: `${15 + Math.random() * 70}%`,
    top: `${25 + Math.random() * 55}%`,
    background: ['crimson', 'gold', 'dodgerblue', 'deeppink'][Math.floor(Math.random() * 4)]
  }))
)

const lights = ref(
  Array.from({ length: 30 }).map(() => ({
    left: `${10 + Math.random() * 80}%`,
    top: `${25 + Math.random() * 55}%`,
    animationDelay: `${Math.random() * 2}s`
  }))
)

const fireworks = ref([])

onMounted(() => {
  setInterval(() => {
    const colors = ['#ff4d4d', '#ffd93d', '#4dd2ff', '#c77dff', '#5cff8d']

    fireworks.value.push({
      style: {
        left: `${Math.random() * 90}%`,
        top: `${Math.random() * 40}%`,
        '--c': colors[Math.floor(Math.random() * colors.length)]
      }
    })

    setTimeout(() => fireworks.value.shift(), 1800)
  }, 1200)
})
</script>

<style scoped>
.scene {
  height: 100vh;
  background: radial-gradient(circle at bottom, #021024, #000);
  position: relative;
  overflow: hidden;
}

/* ⭐ STAR */
.star {
  position: absolute;
  top: 10%;
  left: 50%;
  transform: translateX(-50%);
  font-size: 80px;
  color: gold;
  text-shadow: 0 0 30px gold, 0 0 60px gold;
  animation: pulse 2s infinite alternate;
}

@keyframes pulse {
  from {
    transform: translateX(-50%) scale(1);
  }

  to {
    transform: translateX(-50%) scale(1.2);
  }
}

/* 🎄 TREE */
.tree {
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%) scale(1.3);
}

.layer {
  width: 0;
  border-left: 180px solid transparent;
  border-right: 180px solid transparent;
  border-bottom: 220px solid #0a7a3a;
  margin-top: -90px;
}

.l2 {
  border-bottom-width: 240px;
}

.l3 {
  border-bottom-width: 260px;
}

.l4 {
  border-bottom-width: 280px;
}

/* 🎈 ORNAMENTS */
.ornament {
  position: absolute;
  width: 22px;
  height: 22px;
  border-radius: 50%;
  box-shadow: 0 0 10px rgba(255, 255, 255, 0.6);
}

/* ✨ LIGHTS */
.light {
  position: absolute;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: yellow;
  animation: blink 1.2s infinite alternate, hue 4s infinite linear;
}

@keyframes blink {
  from {
    opacity: 0.4;
  }

  to {
    opacity: 1;
  }
}

@keyframes hue {
  to {
    filter: hue-rotate(360deg);
  }
}

/* 🎆 FIREWORKS */
.firework {
  position: absolute;
  width: 10px;
  height: 10px;
}

.firework span {
  position: absolute;
  width: 4px;
  height: 40px;
  background: var(--c);
  top: 0;
  left: 3px;
  transform-origin: bottom;
  animation: spark 1.6s ease-out forwards;
}

@keyframes spark {
  from {
    transform: rotate(var(--r)) scaleY(0);
    opacity: 1;
  }

  to {
    transform: rotate(var(--r)) scaleY(1.5);
    opacity: 0;
  }
}
</style>
