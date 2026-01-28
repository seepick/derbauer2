# Probability

* about:
  * things need to be more random, more unpredictable to keep the game interesting
  * introduce randomness/unpredictability into the game; make it all "fuzzier"
* affects many areas: production/consumption variability, happenings, ...
* send input through "machine" to get random-like output value
* distributions:
  * linear/exp/log chance; binominal distribution/coefficient
  * "uniform distribution": complete random
  * "normal distribution": gaussian
  * time-dependent: keep memory of past executions
    * e.g. for cooldowns: number of tries since last success increases probability

## Implementation

* what does the API look like? what kind of parameters express the needs to design a good experience?
* java.util.Random.nextGaussian() * deviation + mean
* params:
  1. expected value/mean mu: where the maximum will be on the x-axis
  1. variance^2: standard deviation, how far away from mean, >0
* help:
  * basic math: https://matheguru.com/stochastik/normalverteilung.html
  * for game dev: https://www.alanzucconi.com/2015/09/09/understanding-the-gaussian-distribution/
  * or use apache math3: https://commons.apache.org/proper/commons-math/userguide/distribution.html

# Ideas

* environment depending probability; thus using modifiers/stats/general game state
  * e.g. rats eat food more likely in winter than summer (introduce concept of season)
