package com.gempukku.tcg.generic.spring;

import com.gempukku.tcg.generic.evaluator.ConstantIntEvaluator;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import org.springframework.core.convert.converter.Converter;

public class StringToIntEvaluatorConverter implements Converter<String, IntEvaluator> {
    @Override
    public IntEvaluator convert(String s) {
        final ConstantIntEvaluator evaluator = new ConstantIntEvaluator();
        evaluator.setValue(Integer.parseInt(s));
        return evaluator;
    }
}
