# VectorImage

## Preface

Change svg vector drawable path color in Android.

改变 `Android` 中 `svg` `Vector` 图 路径 `path` 颜色。

## 原理
Android 源码在 API 24 之前是支持修改 Vector Drawable 对应path颜色的，只是接口没有对外暴露；API 24及其之后没有研究，这里不清楚能否修改。

因此，这里利用反射简单的写了一个操作类去修改。

例如我们有一个 `svg` 格式的 `vector` 图：

```
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="200dp"
    android:height="200dp"
    android:viewportWidth="1024"
    android:viewportHeight="1024">
    <path
        android:name="left1"
        android:fillColor="#FF0000"
        android:pathData="M168.23,270.65c0,-0.02 175.54,-0.02 175.54,-0.02v-58.51h-175.51c-0.01,0 -0.03,58.54 -0.03,58.54zM138.97,212.09c0.01,-16.14 13.1,-29.23 29.24,-29.23 0.01,0 0.03,0 0.05,0h204.77v117.03h-204.77c-0.01,0 -0.02,0 -0.03,0 -16.15,0 -29.24,-13.08 -29.26,-29.23v-58.57z" />
    <path
        android:name="left2"
        android:fillColor="#FF0000"
        android:pathData="M373.03,281.16l36.57,16.88v-113.34l-36.57,16.87v79.59zM343.77,182.86l68.47,-31.6c14.7,-6.79 26.62,0.83 26.62,16.95v146.33c0,16.15 -11.85,23.77 -26.62,16.95l-68.47,-31.6v-117.03zM241.37,292.57h29.26v731.43h-29.26z" />
    <path
        android:name="right1"
        android:fillColor="#4A4A4A"
        android:pathData="M855.77,124.36c0,-0.02 -175.54,-0.02 -175.54,-0.02v-58.51h175.51c0.01,0 0.03,58.54 0.03,58.54zM885.03,65.8c-0.01,-16.14 -13.1,-29.23 -29.24,-29.23 -0.01,0 -0.03,0 -0.05,0h-204.77v117.03h204.77c0.01,0 0.02,0 0.03,0 16.15,0 29.24,-13.08 29.26,-29.23v-58.57z" />
    <path
        android:name="right2"
        android:fillColor="#4A4A4A"
        android:pathData="M650.97,134.88l-36.57,16.88v-113.34l36.57,16.87v79.59zM680.23,36.57l-68.47,-31.6c-14.7,-6.8 -26.62,0.83 -26.62,16.94v146.33c0,16.15 11.85,23.77 26.62,16.95l68.47,-31.6v-117.03zM753.37,446.17v-299.89h29.26v299.89h0.01c8.13,0 14.61,6.53 14.61,14.59v87.86c0,8.05 -6.55,14.59 -14.61,14.59h-0.01v460.8h-29.26v-460.8h-0.01c-0.01,0 -0.03,0 -0.05,0 -8.04,0 -14.56,-6.52 -14.56,-14.56 0,-0.01 0,-0.01 0,-0.02v-87.86c0,-8.05 6.55,-14.59 14.61,-14.59h0.01z" />
</vector>
```

我们给每条 `path` 设置一个 `name` ，那么我们即可在代码中根据不同的 `path` 对应的 `name` 来修改其对应的颜色：

```
 private val leftColor = Color.parseColor("#1AFA29")
 private val rightColor = Color.parseColor("#FF0000")
    
val drawable =  VectorImage(this, R.drawable.ic_headset)
    .setPartPathColor("left1", leftColor, true)
    .setPartPathColor("left2", leftColor, true)
    .setPartPathColor("right1", rightColor, true)
    .setPartPathColor("right2", rightColor, true)
    .create()

findViewById<AppCompatImageView>(R.id.iv).setImageDrawable(drawable)
```    

