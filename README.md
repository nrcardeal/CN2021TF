Para a correta execução deste trabalho é pressuposto a existência de alguns pré-requisitos:
* Java 11 ou superior;
* Maven ou um IDE que possa executar as _tasks_ do mesmo;
* Um projeto GCP e conhecimento sobre a tecnologia;
* Ativação das APIs Cloud Vision API, Cloud Translation API e Cloud Build API no projeto GCP referido no ponto anterior;
* Uma conta de serviço GCP que tenha acesso a leituras e escritas para _Cloud Storage_ e _Firestore_;
* 2 tópicos _Pub/Sub_ com o nome _topicworkers_ e _g12-t1d-tf-topic_, e uma subscrição para o primeiro com o nome _topicworkers-sub_;
* Criação de um _bucket_ com o nome g12-t1-v2021-tf-eu na _Cloud Storage_;

## Passo 1 - Preparação dos Executáveis
É necessário a execução das _tasks_ _package_ e _install_ para todos os projetos Maven existentes neste repositório, excetuando os dois projetos CloudFunction que a execução destas _tasks_ é opcional visto que quando for realizado o seu _deployment_ para o projeto GCP as mesmas aceitam o código raíz ou o ficheiro executável.

## Passo 2 - Preparação do projeto GCP
Devem ser criados 2 _instance groups_ com imagens _templates_ de duas instancias de VM, uma onde se encontre o ficheiro .jar executável do servidor e outra onde se encontre o ficheiro .jar executável da LabelsApp. Para ambas as VMs é necessário ainda que ambas apresentem um ficheiro .JSON com o _token_ de acesso à conta de serviço e criação de um _startup script_ que inicie as duas imagens executáveis e apresente as variáveis de ambiente necessárias à execução de ambas.

Variáveis de ambiente:
``` 
Server:

GOOGLE_APPLICATION_CREDENTIALS=<path para o ficheiro .JSON>
PROJECT_ID=<id do projeto gcp>
PORT=<porto onde pretende aceder ao servidor>

LabelsApp:

GOOGLE_APPLICATION_CREDENTIALS=<path para o ficheiro .JSON>
PROJECT_ID=<id do projeto gcp>
```

De seguida inicia-se o _deployment_ das _Cloud Functions_ sendo precisa uma _Cloud Function HTTP_ para onde será feito o _upload_ de um ficheiro .zip que contenha o ficheiro .jar executável do projeto CloudFunctionLookup ou o código raíz do mesmo. Para a outra _Cloud Function_ procede-se de forma semelhante, no entanto esta é do tipo _Cloud Function Pub/Sub_ e tem ligada a conta de serviço mencionada nos pré-requisitos.

## Passo 3 - Executar o Programa
Neste momento é possível por a correr as _instance groups_ e as _Cloud Functions_. Para correr o cliente, basta executar o ficheiro executável do mesmo gerado no 1º Passo e será apresentado um menú com as diversas funcionalidades do sistema. Anota-se que para o _upload_ de uma imagem é nesseçário indicar o caminho completo da mesma. 