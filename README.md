Latest Version
==============
Please see the latest version of PE-Trimmer:https://github.com/bioinfomaticsCSU/PE-Trimmer


License
=======

Copyright (C) 2017 Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see <http://www.gnu.org/licenses/>.

Jianxin Wang(jxwang@mail.csu.edu.cn), Xingyu Liao(liaoxingyu@csu.edu.cn)
School of Information Science and Engineering
Central South University
ChangSha
CHINA, 410083


Installation and operation of PE-Trimmer 
==================================

### Dependencies

When running PE-Trimmer from GitHub source the following tools are
required:
* [jdk.1.8.0](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [dsk.2.1.0](http://minia.genouest.org/dsk/)

### Add system environment variables
The user can modify the system environment variables with the following commands:

vim /etc/profile

export JAVA_HOME="/usr/local/jdk1.8.0_20/bin"
export DSK_HOME="/home/.../dsk-2.1.0-Linux/bin"
export PATH="$JAVA_HOME:$DSK_HOME:$PATH"

source /etc/profile
 
### Install PE-Trimmer

PE-Trimmer automatically compiles all its sub-parts when needed (on the first use). 
Thus, installation is not required.

### Run PE-Trimmer.

1) Loading lib to PE-Trimmer
    
	Before running PE-Trimmer, we need to load the library files into lib folder(/home/.../PE-Trimmer/lib/).
	
	For example:
	
	One library (The library is composed of paired-end reads).
	
	/home/.../PE-Trimmer/lib/frag_1.fastq (The left mate reads of the library)
	
	/home/.../PE-Trimmer/lib/frag_2.fastq (The right mate reads of the library)
	
2) Edit the configuration:
    
	Before running PE-Trimmer, we also need configure the config.txt(/home/.../PE-Trimmer/config.txt).
    
	For example:
    
    home=/homed/liaoxingyu/PE-Trimmer
    
	lib_left_name=reads_1
    
	lib_right_name=reads_2
    
	Readlength=100
    
	WindowSize=11
    
	UserThreshold=0.3
	
	* 'home': The working directory of PE-Trimmer.
	* 'lib_left_name': The name of the left fastq file.
	* 'lib_right_name': The name of the right fastq file.
	* 'Readlength': The average length of reads.
	* 'WindowSize':  Window size setting during trimming. 
	* 'UserThreshold': Trimming threshold. The default value of it is 0.3. It can also be manually set by the user.
    
3) Run the following command to start the PE-Trimmer.
     
	cd /home/.../PE-Trimmer
	./run.sh
    
	If the system prompts "operation not permitted" ,we need to run the following commands to modify the permissions of ARC-master folder at this time.
    
	cd ..
	chmod -R 777  PE-Trimmer
	cd PE-Trimmer

### Output.
    
    /home/.../PE-Trimmer/TrimInformations/Trimed.paired_right.fq
	
	/home/.../PE-Trimmer/TrimInformations/Trimed.paired_left.fq

    /home/.../PE-Trimmer/TrimInformations/Unpaired_left.fq
	
	/home/.../PE-Trimmer/TrimInformations/Unpaired_right.fq

### Evaluate the trimming effect of PE-Trimmer.
   	
	cd /home/.../PE-Trimmer/Test
	./Test.sh
    
	The user needs to read the instructions in the "Test" foler.
