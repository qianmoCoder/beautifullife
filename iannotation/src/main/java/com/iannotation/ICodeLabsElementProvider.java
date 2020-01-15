package com.iannotation;

import com.iannotation.model.CodeLabsData;

import java.util.List;

public interface ICodeLabsElementProvider {
    List<CodeLabsData> getCodeLabsData(String url);
}
