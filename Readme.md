# Exif Manager

一个用于处理照片 exif 信息的工具，目前支持 JPG 格式的照片

## 功能

- 加载 JPG 图片
- 对图片 exif 信息进行操作
    - 查看图片 exif 信息
    - 删除全部 exif 信息
    - 删除指定 exif tag
    - TODO
    
## 命令

```shell script
usage: Exif Manager
 -f,--file <arg>        load image
 -h,--help              show help
 -o,--operation <arg>   operations:
                        showAll: show all exif info
                        removeAll <destination file>: remove all exif info
                        and save to destination file
                        removeTag <destination file> <Tag>: remove exif
                        tag and save to destination file, tag = APERTURE

```

## 开发流程

1. clone 本项目到本地 `git clone https://github.com/Creedowl/ExifManager.git`
2. 修改代码进行开发
3. 构建项目 `./gradlew build` ，或者直接运行 `./gradlew run`
4. 向原项目发起pr请求

## 测试流程

执行测试命令 `./gradlew test` ，根据测试结果对代码进行修正，对新代码添加测试用例

## 缺陷提交

请在仓库中发起 [issue](https://github.com/Creedowl/ExifManager/issues/new)

## 任务计划

- [x] 基础功能
- [ ] 命令要求优先级
- [ ] 替换桩模块
