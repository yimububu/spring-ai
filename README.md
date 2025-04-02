#### Chat Client API

ChatClient 是屏蔽底层 AI 模型差异性的客户端接口，不针对特定的 LLMs 提供商，是一个比 ChatModel 更高级的API。为 LLMs
提供直接入口，它通过 ChatModel 构建而来。
Spring AI 基于 ChatClient API，提供了一系列的 Advisor API，用来增强 ChatClient 的能力，其内部实现和 AOP 同理。

#### Chat Model API

ChatModel 是针对于各个 AI 模型的客户端，
在原有的三种不同角色设定（System、Assistant、User）基础上，新增了 Function Calling 功能（function 角色）。

#### Function Calling API
在 AI 模型中集成 Function Calling，使 AI 模型能够请求执行客户端函数，从而根据需要动态获取必要信息或执行任务。
##### 如何工作？

假设我们希望 AI 模型响应它自身没有的信息，例如某个给定地点的当前温度。
我们可以给 AI 模型提供关于我们自定义函数的元数据，使 AI 模型在处理我们提示词（prompt）时调用这些函数来获取相关信息。
比如，在处理提示词（prompt）的过程中，如果 AI 模型确定需要获取给定的某个位置的温度信息，它将启动一次服务器端生成的请求/响应交互。AI
模型不会直接返回最终的响应消息，而是会返回一个特殊的工具调用（Tool Call）请求，其中包含函数名称和参数（以 JSON
形式提供）。客户端需要处理该消息，执行指定的函数，并将执行结果作为工具响应（Tool Response）消息返回给 AI 模型。

Function Calling 主要有两个核心使用场景：

1. **获取数据（Fetching Data）**：获取最新信息并整合到模型的响应中（RAG），适用于搜索知识库以及从 API 检索特定数据（例如当前天气数据）。
2. **执行操作（Taking Action）**：执行操作，例如提交表单、调用 API、修改应用程序状态（包括前端或后端），或采取代理工作流操作（如移交对话）。

Function calling steps：

1. Call model with functions defined – along with your system and user messages.
2. Model decides to call function(s) – model returns the name and input arguments.
3. Execute function code – parse the model's response and handle function calls.
4. Supply model with results – so it can incorporate them into its final response.
5. Model responds – incorporating the result in its output.


##### spring 实现

Spring AI 大大简化了支持函数调用所需的代码。它为你代理了函数调用的交互过程。你只需将函数定义为一个
@Bean，然后在提示词（Prompt）选项中提供该 Bean 的名称，或者直接将函数作为参数传递到提示请求选项中即可。你还可以在提示（Prompt）中引用多个函数
Bean 的名称。

从 FunctionCallback 迁移到 ToolCallback API


#### 向量数据库（Vector Databases）
向量数据库在 AI 应用中发挥着至关重要的作用。
在向量数据库中，查询与传统关系数据库有所不同。它们执行的是相似性搜索，而非精确匹配。当给定一个向量作为查询时，向量数据库会返回与该查询向量“相似”的向量。
向量数据库用于将你的数据与AI模型集成。使用的第一步是将数据加载到向量数据库中。然后，当用户查询需要发送到AI模型时，首先会检索一组相似的文档。这些文档作为用户问题的上下文，并与用户的查询一起发送给AI模型。这种技术被称为检索增强生成（RAG）。



#### 消息角色和指令执行（Message roles and instruction following）
你可以通过`消息角色`向 AI 模型提供具有不同权限级别的`指令（提示词）`。
OpenAI 模型规范描述了我们的模型**如何根据不同的消息角色给予消息不同的优先级。**