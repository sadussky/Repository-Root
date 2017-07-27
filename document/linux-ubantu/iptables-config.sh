

＃删除原来 iptables 里面已经有的规则
iptables -F
iptables -X

＃抛弃所有不符合三种链规则的数据包
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

＃设置：本地进程 lo 的 INPUT 和 OUTPUT 链接 ； eth1的 INPUT链
iptables -A INPUT -i lo -j ACCEPT
iptables -A INPUT -i eth1 -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A INPUT -i eth1 -m state --state NEW,INVALID -j LOG
iptables -A OUTPUT -o lo -j ACCEPT

＃对其他主要允许的端口的 OUTPUT设置：
＃ DNS
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 53 -j ACCEPT
iptables -A OUTPUT -o eth1 -p UDP --sport 1024:65535 --dport 53 -j ACCEPT

＃HTTP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 80 -j ACCEPT

#HTTPS
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 443 -j ACCEPT

#Email 接受 和发送
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 110 -j ACCEPT
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 25 -j ACCEPT

＃ FTP 数据和控制
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 20 -j ACCEPT
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 21 -j ACCEPT

＃DHCP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 68 -j ACCEPT
iptables -A OUTPUT -o eth1 -p UDP --sport 1024:65535 --dport 68 -j ACCEPT

#POP3S Email安全接收
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 995 -j ACCEPT

＃时间同步服务器 NTP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 123 -j ACCEPT

#拒绝 eth1 其他剩下的
iptables -A OUTPUT -o eth1 --match state --state NEW,INVALID -j LOG


#最后是有关于iptables存储的命令： 
iptables-save > /etc/iptables.up.rule




