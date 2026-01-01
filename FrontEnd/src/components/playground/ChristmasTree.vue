<template>
  <div class="scene">
    <!-- ⭐ Star -->
    <div class="star">★</div>
    <div class="star-glow"></div>

    <!-- 🌙 Moon -->
    <div class="moon"></div>

    <!-- ❄️ Snowflakes -->
    <div v-for="(snow, i) in snowflakes" :key="'snow-' + i" class="snowflake" :style="snow"></div>

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

      <!-- ✨ Sparkles -->
      <span v-for="(s, i) in sparkles" :key="'sparkle-' + i" class="sparkle" :style="s"></span>
    </div>

    <!-- 🎁 Presents -->
    <div v-for="(p, i) in presents" :key="'present-' + i" class="present" :style="p"></div>

    <!-- 🎆 Fireworks -->
    <div v-for="(f, i) in fireworks" :key="i" class="firework" :style="f.style">
      <span v-for="n in 12" :key="n" :style="{ transform: `rotate(${n * 30}deg)` }" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const ornaments = ref(
  Array.from({ length: 24 }).map(() => ({
    left: `${15 + Math.random() * 70}%`,
    top: `${25 + Math.random() * 55}%`,
    background: ['crimson', 'gold', 'dodgerblue', 'deeppink', '#ff6b6b', '#4ecdc4'][Math.floor(Math.random() * 6)],
    animationDelay: `${Math.random() * 2}s`,
    animationDuration: `${2 + Math.random() * 2}s`
  }))
)

const lights = ref(
  Array.from({ length: 40 }).map(() => ({
    left: `${10 + Math.random() * 80}%`,
    top: `${25 + Math.random() * 55}%`,
    animationDelay: `${Math.random() * 2}s`
  }))
)

const sparkles = ref(
  Array.from({ length: 15 }).map(() => ({
    left: `${20 + Math.random() * 60}%`,
    top: `${30 + Math.random() * 50}%`,
    animationDelay: `${Math.random() * 3}s`,
    animationDuration: `${1.5 + Math.random() * 1.5}s`
  }))
)

const snowflakes = ref(
  Array.from({ length: 50 }).map(() => ({
    left: `${Math.random() * 100}%`,
    top: `${-10 + Math.random() * 10}%`,
    animationDelay: `${Math.random() * 5}s`,
    animationDuration: `${3 + Math.random() * 4}s`,
    opacity: 0.6 + Math.random() * 0.4
  }))
)

const presents = ref(
  Array.from({ length: 6 }).map((_, i) => ({
    left: `${20 + i * 15}%`,
    bottom: '5%',
    background: ['#ff4d4d', '#4dd2ff', '#ffd93d', '#c77dff', '#5cff8d', '#ff6b6b'][i],
    animationDelay: `${i * 0.2}s`
  }))
)

const fireworks = ref([])
let fireworkInterval = null
let snowInterval = null

onMounted(() => {
  // Fireworks
  fireworkInterval = setInterval(() => {
    const colors = ['#ff4d4d', '#ffd93d', '#4dd2ff', '#c77dff', '#5cff8d', '#ff6b6b', '#ffa500', '#ff1493']
    
    for (let i = 0; i < 2; i++) {
      setTimeout(() => {
        fireworks.value.push({
          style: {
            left: `${10 + Math.random() * 80}%`,
            top: `${10 + Math.random() * 40}%`,
            '--c': colors[Math.floor(Math.random() * colors.length)]
          }
        })
        setTimeout(() => {
          const index = fireworks.value.findIndex(f => f.style === fireworks.value[fireworks.value.length - 1].style)
          if (index > -1) fireworks.value.splice(index, 1)
        }, 2000)
      }, i * 300)
    }
  }, 2000)

  // Continuous snow
  snowInterval = setInterval(() => {
    snowflakes.value.forEach(snow => {
      if (parseFloat(snow.top) > 110) {
        snow.top = `${-10}%`
        snow.left = `${Math.random() * 100}%`
      }
    })
  }, 100)
})

onUnmounted(() => {
  if (fireworkInterval) clearInterval(fireworkInterval)
  if (snowInterval) clearInterval(snowInterval)
})
</script>

<style scoped>
.scene {
  height: 100vh;
  background: radial-gradient(ellipse at bottom, #1a1a2e 0%, #0f0f1e 50%, #000 100%);
  position: relative;
  overflow: hidden;
}

/* ⭐ STAR */
.star {
  position: absolute;
  top: 8%;
  left: 50%;
  transform: translateX(-50%);
  font-size: 100px;
  color: gold;
  text-shadow: 
    0 0 20px gold,
    0 0 40px gold,
    0 0 60px gold,
    0 0 80px rgba(255, 215, 0, 0.5);
  animation: starPulse 2s infinite alternate, starRotate 10s infinite linear;
  z-index: 10;
}

.star-glow {
  position: absolute;
  top: 8%;
  left: 50%;
  transform: translateX(-50%);
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(255, 215, 0, 0.3) 0%, transparent 70%);
  border-radius: 50%;
  animation: glowPulse 3s infinite alternate;
  z-index: 9;
}

@keyframes starPulse {
  0% {
    transform: translateX(-50%) scale(1);
    filter: brightness(1);
  }
  100% {
    transform: translateX(-50%) scale(1.3);
    filter: brightness(1.5);
  }
}

@keyframes starRotate {
  0% {
    transform: translateX(-50%) rotate(0deg);
  }
  100% {
    transform: translateX(-50%) rotate(360deg);
  }
}

@keyframes glowPulse {
  0% {
    transform: translateX(-50%) scale(0.8);
    opacity: 0.5;
  }
  100% {
    transform: translateX(-50%) scale(1.2);
    opacity: 0.8;
  }
}

/* 🌙 MOON */
.moon {
  position: absolute;
  top: 15%;
  right: 15%;
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
  border-radius: 50%;
  box-shadow: 
    0 0 30px rgba(254, 243, 199, 0.6),
    inset -20px -10px 0 rgba(0, 0, 0, 0.1);
  animation: moonFloat 6s infinite ease-in-out;
  z-index: 5;
}

@keyframes moonFloat {
  0%, 100% {
    transform: translateY(0) translateX(0);
  }
  50% {
    transform: translateY(-20px) translateX(10px);
  }
}

/* ❄️ SNOWFLAKES */
.snowflake {
  position: absolute;
  color: white;
  font-size: 20px;
  font-weight: bold;
  text-shadow: 0 0 5px rgba(255, 255, 255, 0.8);
  animation: snowFall linear infinite;
  pointer-events: none;
}

.snowflake::before {
  content: '❄';
}

@keyframes snowFall {
  0% {
    transform: translateY(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: translateY(110vh) rotate(720deg);
    opacity: 0;
  }
}

/* 🎄 TREE */
.tree {
  position: absolute;
  bottom: 8%;
  left: 50%;
  transform: translateX(-50%) scale(1.3);
  filter: drop-shadow(0 0 30px rgba(10, 122, 58, 0.5));
  animation: treeSway 4s infinite ease-in-out;
}

@keyframes treeSway {
  0%, 100% {
    transform: translateX(-50%) scale(1.3) rotate(0deg);
  }
  50% {
    transform: translateX(-50%) scale(1.3) rotate(1deg);
  }
}

.layer {
  width: 0;
  border-left: 180px solid transparent;
  border-right: 180px solid transparent;
  border-bottom: 220px solid #0a7a3a;
  margin-top: -90px;
  position: relative;
  animation: layerGlow 3s infinite alternate;
}

@keyframes layerGlow {
  0% {
    filter: brightness(1);
  }
  100% {
    filter: brightness(1.2);
  }
}

.l2 {
  border-bottom-width: 240px;
  border-bottom-color: #0d9548;
}

.l3 {
  border-bottom-width: 260px;
  border-bottom-color: #0fa552;
}

.l4 {
  border-bottom-width: 280px;
  border-bottom-color: #12b55c;
}

/* 🎈 ORNAMENTS */
.ornament {
  position: absolute;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  box-shadow: 
    0 0 15px rgba(255, 255, 255, 0.8),
    inset -5px -5px 10px rgba(0, 0, 0, 0.3);
  animation: ornamentSwing 3s infinite ease-in-out;
  z-index: 2;
}

@keyframes ornamentSwing {
  0%, 100% {
    transform: translateX(0) translateY(0) rotate(0deg);
  }
  25% {
    transform: translateX(3px) translateY(-2px) rotate(5deg);
  }
  75% {
    transform: translateX(-3px) translateY(2px) rotate(-5deg);
  }
}

/* ✨ LIGHTS */
.light {
  position: absolute;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: yellow;
  box-shadow: 
    0 0 10px currentColor,
    0 0 20px currentColor,
    0 0 30px currentColor;
  animation: lightBlink 1.2s infinite alternate, lightHue 4s infinite linear, lightPulse 2s infinite;
  z-index: 1;
}

@keyframes lightBlink {
  0% {
    opacity: 0.3;
    transform: scale(0.8);
  }
  100% {
    opacity: 1;
    transform: scale(1.2);
  }
}

@keyframes lightHue {
  0% {
    filter: hue-rotate(0deg);
  }
  100% {
    filter: hue-rotate(360deg);
  }
}

@keyframes lightPulse {
  0%, 100% {
    box-shadow: 
      0 0 10px currentColor,
      0 0 20px currentColor,
      0 0 30px currentColor;
  }
  50% {
    box-shadow: 
      0 0 15px currentColor,
      0 0 30px currentColor,
      0 0 45px currentColor;
  }
}

/* ✨ SPARKLES */
.sparkle {
  position: absolute;
  width: 6px;
  height: 6px;
  background: white;
  border-radius: 50%;
  box-shadow: 0 0 10px white, 0 0 20px white;
  animation: sparkleTwinkle 2s infinite;
  z-index: 3;
}

@keyframes sparkleTwinkle {
  0%, 100% {
    opacity: 0;
    transform: scale(0);
  }
  50% {
    opacity: 1;
    transform: scale(1.5);
  }
}

/* 🎁 PRESENTS */
.present {
  position: absolute;
  width: 60px;
  height: 60px;
  border-radius: 8px;
  box-shadow: 
    0 5px 15px rgba(0, 0, 0, 0.3),
    inset 0 2px 5px rgba(255, 255, 255, 0.2);
  animation: presentBounce 2s infinite ease-in-out;
  z-index: 1;
}

.present::before {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 4px;
  height: 100%;
  background: rgba(255, 255, 255, 0.5);
}

.present::after {
  content: '🎀';
  position: absolute;
  top: -10px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 20px;
  animation: bowWiggle 1.5s infinite ease-in-out;
}

@keyframes presentBounce {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-10px) rotate(2deg);
  }
}

@keyframes bowWiggle {
  0%, 100% {
    transform: translateX(-50%) rotate(0deg);
  }
  25% {
    transform: translateX(-50%) rotate(-5deg);
  }
  75% {
    transform: translateX(-50%) rotate(5deg);
  }
}

/* 🎆 FIREWORKS */
.firework {
  position: absolute;
  width: 10px;
  height: 10px;
  z-index: 20;
}

.firework span {
  position: absolute;
  width: 5px;
  height: 50px;
  background: var(--c);
  top: 0;
  left: 2.5px;
  transform-origin: bottom;
  box-shadow: 0 0 10px var(--c);
  animation: fireworkSpark 2s ease-out forwards;
}

@keyframes fireworkSpark {
  0% {
    transform: rotate(var(--r, 0deg)) scaleY(0);
    opacity: 1;
  }
  50% {
    opacity: 1;
  }
  100% {
    transform: rotate(var(--r, 0deg)) scaleY(2);
    opacity: 0;
  }
}
</style>
