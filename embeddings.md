#### 向量嵌入（Vector embeddings）
了解如何将文本转换为数字，从而解锁`搜索`等应用场景。

#### 什么是 embeddings？
OpenAI 的文本嵌入用于衡量文本字符串之间的关联程度。embeddings 通常用于以下应用：
- 搜索（按查询字符串的相关性对结果进行排序）
- 聚类（按相似性对文本字符串进行分组）
- 推荐（推荐具有相关文本字符串的项目）
- 异常检测（识别关联度较低的异常值）
- 多样性衡量（分析相似性分布）
- 分类（根据最相似的标签对文本字符串进行分类）

**嵌入是一个由`浮点数`组成的`向量（列表）`。两个向量之间的距离表示它们的关联程度，距离较小表明高度相关，距离较大则表明相关性较低。**

#### 如何获取嵌入（Embeddings）
要获取嵌入（embedding），请将文本字符串与嵌入模型名称（例如 text-embedding-3-small）一起发送到 OpenAI 的嵌入 API 端点。
```
curl https://api.openai.com/v1/embeddings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
    "input": "Your text string goes here",
    "model": "text-embedding-3-small",
    "dimensions": 1024
  }'
```
API 返回的响应包含嵌入向量（即浮点数列表）以及一些额外的元数据。你可以提取嵌入向量，将其存储到 向量数据库（如 Pinecone、Weaviate、FAISS、Milvus 等），然后用于多个 AI 应用场景。
```
{
  "object": "list",
  "data": [
    {
      "object": "embedding",
      "index": 0,
      "embedding": [
        -0.006929283495992422,
        -0.005336422007530928,
        -4.547132266452536e-05,
        -0.024047505110502243
      ],
    }
  ],
  "model": "text-embedding-3-small",
  "usage": {
    "prompt_tokens": 5,
    "total_tokens": 5
  }
}
```
默认情况下，text-embedding-3-small 生成的嵌入向量长度为 1536 维，而 text-embedding-3-large 生成的嵌入向量长度为 3072 维。
如果需要降低嵌入向量的维度，以减少存储和计算成本，同时尽可能保留语义信息，可以在请求 API 时使用 dimensions 参数。