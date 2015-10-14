j360
====
本工程开发IDE为Intellij IDEA，工程讲解也完全按照该工具进行。<br />
!!!建议用eclipse的童鞋可以尽快切换IDEA进行开发和学习，以便可以快速掌握maven和git的基本使用技巧。!!!<br />
1、安装说明：<br />

2、开发说明：<br />


All in j360 for J2EE.<br />


Maven基础
----




Git基础
----
###这里使用两个分支来进行工程开发<br />
1）development：本地开发和调试使用该分支<br />
2）master：功能完成并且达到发布要求提交至该分支，按照版本控制要求赋予本次提交一个Tag为版本号<br />

###使用Idea Terminal窗口<br />
###1、查看Git分支的命令，*代表当前分支<br />
$git branch<br />
结果：<br />
* development<br />
  master<br />
:切换分支<br />
$git checkout<br />

###2、合并分支，development开发完成一个功能，需要发布时<br />
####a切换至Master分支、查看<br />
####b合并Development分支里面的内容<br />
* $git checkout -b development
* $git branch
* $git merge development

###3、IDE手动切换分支<br />
右键工程 - git - Repository - Branches - Local Branches - 下面选择对应的分支 - checkout即可切换<br />

###4、提交一次到本地<br />
右键工程 - git - Commit Directory - Commit / Commit and Push :Push是同时再把本地仓库提交到远程仓库<br />

###5、IDE手动合并分支<br />
* 1)手动切换分支<br />
* 2）右键工程 - git - Repository - Branches - Local Branches - 下面选择对应的分支 - Merge<br />
* 3)完成、提交一次到本地 - 提交到远程服务器<br />

###模块设计###
- j360-base
- j360-core
- j360-web