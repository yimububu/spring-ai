package org.springframework.ai.chat.client;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * 通过 {@link ChatClient.Builder#build()}  方法创建的 {@link ChatClient} 的默认实现。
 *
 * @author Mark Pollack
 * @author Christian Tzolov
 * @author Josh Long
 * @author Arjen Poutsma
 * @since 1.0.0 M1
 */
class DefaultChatClient implements ChatClient {

    /**
     * LLM 模型
     */
    private final ChatModel chatModel;

    private final ChatClientRequest defaultChatClientRequest;

    public DefaultChatClient(ChatModel chatModel, ChatClientRequest defaultChatClientRequest) {
        this.chatModel = chatModel;
        this.defaultChatClientRequest = defaultChatClientRequest;
    }

    @Override
    public ChatClientRequest prompt() {
        return new ChatClientRequest(this.defaultChatClientRequest);
    }

    @Override
    public ChatClientPromptRequest prompt(Prompt prompt) {
        return new ChatClientPromptRequest(this.chatModel, prompt);
    }

    /**
     * Return a {@code ChatClient.Builder} to create a new {@code ChatClient} whose
     * settings are replicated from this {@code ChatClientRequest}.
     */
    @Override
    public Builder mutate() {
        return this.defaultChatClientRequest.mutate();
    }

    /**
     * use the new fluid DSL starting in {@link #prompt()}
     *
     * @param prompt the {@link Prompt prompt} object
     * @return a {@link ChatResponse chat response}
     */
    @Deprecated(forRemoval = true, since = "1.0.0 M1")
    @Override
    public ChatResponse call(Prompt prompt) {
        return this.chatModel.call(prompt);
    }

}