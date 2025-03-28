package org.springframework.ai.model;

import org.springframework.ai.chat.messages.Media;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 包含内容和元数据的数据结构。{@link org.springframework.ai.document.Document}和{@link org.springframework.ai.chat.messages.Message}共同父类。
 *
 * @author Mark Pollack
 * @author Christian Tzolov
 * @since 1.0.0
 */
public interface Content {

    /**
     * 获取消息的内容。
     */
    String getContent(); // TODO consider getText

    /**
     * 获取与内容相关的媒体。
     */
    default Collection<Media> getMedia() {
        return getMedia("");
    }

    /**
     * 检索与内容相关的媒体附件集合。
     *
     * @param dummy 一个虚拟参数，用于确保方法签名的唯一性。
     * @return 表示媒体附件的 Media 对象列表。
     * @deprecated 自版本 1.0.0 M1 起，该方法已弃用，并将在未来的版本中移除。
     */
    @Deprecated(since = "1.0.0 M1", forRemoval = true)
    List<Media> getMedia(String... dummy);

    /**
     * 返回与内容相关的元数据。
     */
    Map<String, Object> getMetadata();

}
