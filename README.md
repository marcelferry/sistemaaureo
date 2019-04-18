# Projeto base do Sistema de Metas

## Setup do servidor na Reunião Nacional

Usar Ubuntu 16.04+ (Testado no 16.04)

Vamos instalar o SSH Server:

```
sudo apt-get install openssh-server 
```

Vamos instalar o dooku:

```
wget https://raw.githubusercontent.com/dokku/dokku/v0.15.4/bootstrap.sh

sudo DOKKU_TAG=v0.15.4 bash bootstrap.sh 
```

Agora instalar a versão do postgres para o dokku:

```
sudo dokku plugin:install https://github.com/dokku/dokku-postgres.git postgres
```

Adicionar a chave via root.

```
cat ~/.ssh/id_rsa.pub | ssh root@<SEUIP> dokku ssh-keys:add KEY_NAME
```

Adicionar a chave local:

```
ssh-keygen
sudo dokku ssh-keys:add KEY_NAME ~/.ssh/id_rsa.pub
```


### Preparação na máquina cliente

Fazer a copia da base de código para a máquina local

```
git clone https://github.com/marcelferry/sistemaaureo.git
```

Instalar uma ferramenta para gerenciamento da base de dados postgres. Vamos utilizar o virtual env para rodar dentro do ambiente virtual do python.

```
apt-get install virtualenv python-pip libpq-dev python-dev

virtualenv pgadmin4

cd pgadmin4

source bin/activate
```
Install

```
wget https://ftp.postgresql.org/pub/pgadmin/pgadmin4/v1.4/pip/pgadmin4-1.4-py2.py3-none-any.whl

pip install pgadmin4-1.4-py2.py3-none-any.whl
```

Configure

Write the SERVER_MODE = False in lib/python2.7/site-packages/pgadmin4/config_local.py to configure to run in single-user mode:

```
echo "SERVER_MODE = False" >> lib/python2.7/site-packages/pgadmin4/config_local.py
```

Inicie o pgadmin
 
```
python lib/python2.7/site-packages/pgadmin4/pgAdmin4.py

```

Acesse em http://localhost:5050


## Setup do servidor na Digital Ocean

Vamos criar um servidor na digital ocean utilizando One-Click Apps com Dokku.

Defina a **public key** e depois use no **hostname** o valor "cloud.contratacaodemetas.com.br".

E marque a opção: Use virtualhost naming for apps.

Conecte via ssh a máquina e use o comando abaixo:

Adicione o dominio principal:

```
dokku domains:add-global contratacaodemetas.com.br
```

Crie a aplicação

```
dokku apps:create sistema
```

Instale o plugin do banco de dados:

```
sudo dokku plugin:install https://github.com/dokku/dokku-postgres.git
```

Crie o banco de dados. 

```
dokku postgres:create aureo
```

Faça o link da aplicação com a base.

```
dokku postgres:link aureo sistema
```

Se precisar fazer o backup antes das restauração

```
dokku postgres:export aureo > aureo_20181030.sql
```

Para fazer a restauração

```
dokku postgres:import aureo < aureo_20181030.sql
```

Adicione o servidor remoto no seu projeto de fonte.

```
git remote add dokku dokku@contratacaodemetas:sistema
```
Para publicar a aplicaçâo

```
git push dokku master
```

