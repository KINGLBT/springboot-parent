# GIT学习

+ git init 
+ git add 
+ git commit
+ git status
+ git log
+ git diff
+ git log --pretty=oneline
+ git reflog
+ git reset --hard 1094a
+ git reset --hard HEAD^



HEAD指向的版本就是当前版本，因此，Git允许我们在版本的历史之间穿梭，使用命令git reset --hard commit_id。

穿梭前，用git log可以查看提交历史，以便确定要回退到哪个版本。

要重返未来，用git reflog查看命令历史，以便确定要回到未来的哪个版本。


# 工作区和暂存区

## 工作区（Working Directory）
就是你在电脑里能看到的目录，比如我的learngit文件夹就是一个工作区

## 版本库（Repository）

工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。

## 暂存区（stage或index）
Git的版本库里存了很多东西，其中最重要的就是称为stage（或者叫index）的暂存区，还有Git为我们自动创建的第一个分支master，以及指向master的一个指针叫HEAD。

git add把文件添加进去，实际上就是把文件修改添加到暂存区；
git commit提交更改，实际上就是把暂存区的所有内容提交到当前分支。


## 撤销修改
git checkout -- readme.txt
一种是readme.txt自修改后还没有被放到暂存区，现在，撤销修改就回到和版本库一模一样的状态；
一种是readme.txt已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。

如果文件已经执行了git add，放入暂存区了，如何从暂存区中撤销修改，放回工作区？
用命令git reset HEAD <file>可以把暂存区的修改撤销掉（unstage），重新放回工作区
git reset命令既可以回退版本，也可以把暂存区的修改回退到工作区。当我们用HEAD时，表示最新的版本。



场景1：当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令git checkout -- file。

场景2：当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令git reset HEAD <file>，就回到了场景1，第二步按场景1操作。

场景3：已经提交了不合适的修改到版本库时，想要撤销本次提交，参考版本回退一节，不过前提是没有推送到远程库。


## 删除文件
git checkout其实是用版本库里的版本替换工作区的版本，无论工作区是修改还是删除，都可以“一键还原”。
命令git rm用于删除一个文件。如果一个文件已经被提交到版本库，那么你永远不用担心误删，但是要小心，你只能恢复文件到最新版本，你会丢失最近一次提交后你修改的内容。


# 远程仓库

## 本地仓库与远程仓库关联、

git remote add origin git@github.com:KINGLBT/dem.git

添加后，远程库的名字就是origin，这是Git默认的叫法，也可以改成别的，但是origin这个名字一看就知道是远程库。

## 将本地内容推送到远程仓库

git push -u origin master

把本地库的内容推送到远程，用git push命令，实际上是把当前分支master推送到远程。

由于远程库是空的，我们第一次推送master分支时，加上了-u参数，Git不但会把本地的master分支内容推送的远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。

## 小结
要关联一个远程库，使用命令git remote add origin git@server-name:path/repo-name.git；

关联后，使用命令git push -u origin master第一次推送master分支的所有内容；

此后，每次本地提交后，只要有必要，就可以使用命令git push origin master推送最新修改；

## 从远程库克隆


git clone git@github.com:KINGLBT/dem.git

# 分支管理

## 创建与合并分支

### 创建分支
我们创建dev分支，然后切换到dev分支：git checkout -b dev

git checkout命令加上-b参数表示创建并切换，相当于以下两条命令：

git branch dev
git checkout dev

git branch命令查看当前分支

### 合并分支
我们把dev分支的工作成果合并到master分支上：

+ 切换到master分支上 git checkout master
+ 将dev合并到master上 git branch -d dev

### 删除分支
git branch -d dev

### 小结
Git鼓励大量使用分支：

查看分支：git branch

创建分支：git branch <name>

切换分支：git checkout <name>

创建+切换分支：git checkout -b <name>

合并某分支到当前分支：git merge <name>

删除分支：git branch -d <name>

## 解决冲突

小结
当Git无法自动合并分支时，就必须首先解决冲突。解决冲突后，再提交，合并完成。

解决冲突就是把Git合并失败的文件手动编辑为我们希望的内容，再提交。

用git log --graph命令可以看到分支合并图。

 git log --graph --pretty=oneline --abbrev-commit
