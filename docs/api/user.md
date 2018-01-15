# 用户管理

## 登录

> 登录以便请求后续接口

请求方式：`POST`

请求地址：`/user/login`

请求类型：`application/json`

请求参数：

| 名称       | 类型      | 必需   | 描述     |
| :------- | :------ | :--- | :----- |
| username | String  | 是    | 用户名    |
| password | String  | 是    | 密码     |
| remember | Boolean | 否    | 是否记住登录 |

响应参数：

| 名称       | 类型      | 描述            |
| :------- | :------ | :------------ |
| login    | Boolean | 登录成功          |
| token    | String  | 登录成功后返回的token |
| username | String  | 用户名           |

请求示例：

```json
{
  "username": "test",
  "password": "test",
  "remember": true
}
```

响应示例：

```json
{
  "code": 0,
  "rid": "997cad15-90a4-43ee-ab04-f4cfe28ecd08",
  "msg": "",
  "data": {
    "login": true,
    "username": "test",
    "token": "qpfXDKvElnr/ovn2RTK1ew=="
  },
  "success": true
}
```

## 注册

## 注销

## 查询用户

## 添加用户

## 修改用户

## 删除用户

