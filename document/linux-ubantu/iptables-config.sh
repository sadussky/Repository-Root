

��ɾ��ԭ�� iptables �����Ѿ��еĹ���
iptables -F
iptables -X

���������в�������������������ݰ�
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

�����ã����ؽ��� lo �� INPUT �� OUTPUT ���� �� eth1�� INPUT��
iptables -A INPUT -i lo -j ACCEPT
iptables -A INPUT -i eth1 -m state --state ESTABLISHED,RELATED -j ACCEPT
iptables -A INPUT -i eth1 -m state --state NEW,INVALID -j LOG
iptables -A OUTPUT -o lo -j ACCEPT

����������Ҫ����Ķ˿ڵ� OUTPUT���ã�
�� DNS
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 53 -j ACCEPT
iptables -A OUTPUT -o eth1 -p UDP --sport 1024:65535 --dport 53 -j ACCEPT

��HTTP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 80 -j ACCEPT

#HTTPS
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 443 -j ACCEPT

#Email ���� �ͷ���
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 110 -j ACCEPT
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 25 -j ACCEPT

�� FTP ���ݺͿ���
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 20 -j ACCEPT
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 21 -j ACCEPT

��DHCP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 68 -j ACCEPT
iptables -A OUTPUT -o eth1 -p UDP --sport 1024:65535 --dport 68 -j ACCEPT

#POP3S Email��ȫ����
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 995 -j ACCEPT

��ʱ��ͬ�������� NTP
iptables -A OUTPUT -o eth1 -p TCP --sport 1024:65535 --dport 123 -j ACCEPT

#�ܾ� eth1 ����ʣ�µ�
iptables -A OUTPUT -o eth1 --match state --state NEW,INVALID -j LOG


#������й���iptables�洢����� 
iptables-save > /etc/iptables.up.rule




