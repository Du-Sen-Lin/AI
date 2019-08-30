### AI搜索  
### Java
### N-Puzzle Problem & N-blocks Problem
#### 第一阶段：Searching_student 
 Algorithm:A-star ;Zobrist hashing  
 时间：1s  
 能解决8数码问题和部分15数码样例  
 滑块问题能解决到10阶(一分钟内）  

#### 第二阶段 SearchingAstar
 N-puzzle:IDA-star 反向搜索  
 4 阶的，能够在与老师程序同级别的时间内，解决相同 的问题实例。   
 N-blocks：改进的 A-Star  
 能够在 1 分钟之内解出 分钟之内解出 11 阶的  
 
 #### 第三阶段 SearchingAstar3
  N-puzzle:IDA-star+不相交可加性的静态模式数据库 6-6-3 分区启发式函数优化  
  4阶的复杂样例能在一分钟内解出；（用第二阶段的IDA* 时间为三十分钟左右）  
 
  N-blocks: A-star+剪枝  
  一分钟内解出30阶 （未验证在阶数20-30时是否为最优解）  
 
 #### 可视化代码
  甜学姐天下第一  
