#install java
cd /opt/
sudo wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u101-b13/jdk-8u101-linux-x64.tar.gz"
sudo tar xzf jdk-8u101-linux-x64.tar.gz
cd /opt/jdk1.8.0_101/   
sudo alternatives --install /usr/bin/java java /opt/jdk1.8.0_101/bin/java 2
sudo alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_101/bin/jar 2
sudo alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_101/bin/javac 2
sudo alternatives --set jar /opt/jdk1.8.0_101/bin/jar
sudo alternatives --set javac /opt/jdk1.8.0_101/bin/javac

#install mariadb
sudo yum -y install mariadb-server mariadb
sudo service mariadb start
sudo systemctl enable mariadb.service
mysqladmin -u root password root

#create development database
mysql -uroot -proot -e "CREATE DATABASE product_list CHARACTER SET utf8 COLLATE utf8_general_ci; GRANT ALL PRIVILEGES ON product_list.* TO 'db_user'@'localhost' IDENTIFIED BY 'db_passwd'; GRANT ALL PRIVILEGES ON product_list.* TO 'db_user'@'%' IDENTIFIED BY 'db_passwd'";

#create test database
mysql -uroot -proot -e "CREATE DATABASE product_list_test CHARACTER SET utf8 COLLATE utf8_general_ci; GRANT ALL PRIVILEGES ON product_list_test.* TO 'db_user'@'localhost' IDENTIFIED BY 'db_passwd'; GRANT ALL PRIVILEGES ON product_list_test.* TO 'db_user'@'%' IDENTIFIED BY 'db_passwd'";

#create image folder
sudo mkdir -p /tmp/productlist_img/