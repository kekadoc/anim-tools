# anim-tools


```kotlin
view.animator {
    scale(toX = 1.2f, toY = 1.2f) {
        duration(300L)
        delay(50L)
        interpolator(OvershootInterpolator(4f))
        onEnd {
            //Do something
        }
    }
}.start()
```

```kotlin
progressBar.animator { 
    animate<TextView>(0, 1000) {
        onUpdate {
            target.progress = it.animatedValue as Int
        }
    }
}.start()
```
