package org.example.design.mode.memento;

import java.util.Stack;

public class DraftsBox {
    private final Stack<ArticleMemento> STACK = new Stack<ArticleMemento>();

    public ArticleMemento getMemento() {
        ArticleMemento articleMemento = STACK.pop();
        return articleMemento;
    }

    public void addMemento(ArticleMemento articleMemento) {
        STACK.push(articleMemento);
    }
}
