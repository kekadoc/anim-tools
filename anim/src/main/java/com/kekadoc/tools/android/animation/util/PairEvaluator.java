package com.kekadoc.tools.android.animation.util;

import android.animation.TypeEvaluator;

import androidx.annotation.Nullable;

/**
 * Evaluator с разными типами
 * */
public interface PairEvaluator<R, V> extends TypeEvaluator<Object> {

    @Override
    default R evaluate(float fraction, Object startValue, Object endValue) {
        return quantify(fraction, (V) startValue, (V) endValue);
    }

    @Nullable
    R quantify(float fraction, V startValue, V endValue);

}
