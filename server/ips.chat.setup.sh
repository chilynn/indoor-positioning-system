# !/bin/bash
ln ../chat_server/bin/extra/redhat/openfire /etc/init.d/
ln ../chat_server/bin/extra/redhat/openfire-sysconfig /etc/sysconfig/openfire

chown -R daemon:daemon ../chat_server/

chkconfig --add openfire
