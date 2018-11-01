# Projeto base do Sistema de Metas

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


pg_dump --host=172.17.0.2 --port=5432 -U postgres aureo -f aureo_prod.sql

psql --host=172.17.0.3 --port=5432 -U postgres -d aureobeta -f aureo.sql

