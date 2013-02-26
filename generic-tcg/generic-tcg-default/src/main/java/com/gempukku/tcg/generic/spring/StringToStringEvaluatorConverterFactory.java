package com.gempukku.tcg.generic.spring;

import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import org.springframework.core.convert.converter.Converter;

public class StringToStringEvaluatorConverterFactory implements Converter<String, StringEvaluator> {
    @Override
    public StringEvaluator convert(String s) {
        ConstantStringEvaluator stringEvaluator = new ConstantStringEvaluator();
        stringEvaluator.setValue(s);
        return stringEvaluator;
    }
}
