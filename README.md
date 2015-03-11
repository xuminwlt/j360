j360
====
本工程开发IDE为Intellij IDEA，工程讲解也完全按照该工具进行。
：：：建议用eclipse的童鞋可以尽快切换IDEA进行开发和学习，以便可以快速掌握maven和git的基本使用技巧。
1、安装说明：
2、开发说明：

All in j360 for J2EE.

====
Maven
这里使用两个分支来进行工程开发
1）Development：本地开发和调试使用该分支
2）Master：功能完成并且达到发布要求提交至该分支，按照版本控制要求赋予本次提交一个Tag为版本号



====
Git
使用Idea Terminal窗口
#查看Git分支的命令，*代表当前分支
$git branch
结果：
* development
  master
#切换分支
$git checkout
#IDE手动切换
右键工程 - git - Repository - Branches - Local Branches - 下面选择对应的分支 - checkout即可切换

#提交一次到本地
右键工程 - git - Commit Directory - Commit / Commit and Push :Push是同时再把本地仓库提交到远程仓库


