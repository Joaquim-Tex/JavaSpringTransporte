# Backlog do serviço de transporte

Este backlog descreve o trabalho necessário para entregar os fluxos de consulta de linhas e ônibus e solicitação de embarque e desembarque. Os itens abaixo ainda não estão implementados.

## 1. Definir e persistir linhas, ônibus e paradas

**Objetivo:** fornecer os dados que alimentam as consultas do serviço.

**Instruções:**
- Definir o modelo de linha, ônibus e parada, incluindo identificadores estáveis.
- Associar cada ônibus a uma linha e cada parada à sua posição ordenada nessa linha.
- Restringir a configuração a no máximo três linhas, conforme o requisito atual.
- Registrar para cada ônibus a capacidade, a contagem inicial de passageiros, o horário de saída e o estado (em trânsito ou estacionado).
- Escolher e configurar persistência para que os dados sobrevivam ao reinício da aplicação.

**Critérios de aceite:**
- É possível cadastrar e consultar até três linhas e seus dados associados.
- Não é possível cadastrar uma quarta linha nem passageiros acima da capacidade.
- Os dados permanecem disponíveis após reiniciar a aplicação.
- Testes cobrem validações e relações entre linhas, ônibus e paradas.

## 2. Consultar linhas e ônibus

**Objetivo:** permitir que o usuário escolha uma linha e veja seus ônibus disponíveis.

**Instruções:**
- Criar endpoints para listar as linhas e listar os ônibus de uma linha.
- Retornar para cada ônibus o estado, horário de saída, passageiros atuais e capacidade máxima.
- Definir e documentar como são tratados ônibus estacionados e em trânsito.
- Validar identificadores e responder claramente quando a linha ou o ônibus não existir.

**Critérios de aceite:**
- A consulta de linhas retorna no máximo as linhas configuradas.
- A consulta de uma linha retorna apenas os ônibus associados a ela e os campos de situação e lotação.
- Respostas para identificadores inválidos têm status HTTP apropriado e não retornam dados fictícios.
- Testes cobrem listagem, associação e erros de consulta.

## 3. Consultar percurso, paradas e posição do ônibus

**Objetivo:** apresentar o itinerário e permitir a seleção válida dos pontos de embarque e desembarque.

**Instruções:**
- Expor as paradas da linha em ordem, com seus números/identificadores.
- Registrar e consultar a posição atual do ônibus no percurso.
- Receber os números das paradas em que o usuário pretende subir e descer.
- Validar que as duas paradas pertencem à linha escolhida e que a parada de descida vem depois da de subida no sentido do ônibus.

**Critérios de aceite:**
- O usuário consegue consultar o número e a ordem das paradas e a posição atual do ônibus.
- Seleções de parada inexistente ou de outra linha são rejeitadas.
- Uma descida igual ou anterior à subida é rejeitada; uma descida posterior é aceita.
- Testes cobrem os limites e a ordenação do percurso.

## 4. Registrar solicitação e atualizar lotação por trecho

**Objetivo:** contabilizar um passageiro apenas no trecho em que ele estará no ônibus.

**Instruções:**
- Criar uma operação para registrar uma solicitação com ônibus, parada de subida e parada de descida.
- Atualizar a ocupação de cada trecho entre as paradas selecionadas, sem incrementar os trechos fora desse intervalo.
- Revalidar a capacidade disponível nos trechos antes de confirmar a solicitação.
- Retornar a solicitação confirmada ou uma mensagem de indisponibilidade quando qualquer trecho estiver cheio.

**Critérios de aceite:**
- A solicitação válida aumenta em um passageiro a ocupação dos trechos entre subida e descida.
- A ocupação de trechos antes da subida e depois da descida não muda.
- Uma solicitação que excede a capacidade em algum trecho não é confirmada.
- Testes verificam o cálculo de ocupação em trechos compartilhados e não compartilhados.

## 5. Garantir consistência em solicitações simultâneas

**Objetivo:** impedir que solicitações concorrentes deixem a lotação inconsistente ou acima da capacidade.

**Instruções:**
- Tornar atômica a validação de capacidade e a gravação da solicitação/ocupação.
- Definir o comportamento quando duas solicitações disputam os últimos lugares de um mesmo trecho.
- Evitar que uma falha parcial deixe a solicitação gravada sem atualizar a ocupação, ou o contrário.

**Critérios de aceite:**
- Em solicitações concorrentes, nenhuma confirmação ultrapassa a capacidade de qualquer trecho.
- Falhas não deixam dados parcialmente atualizados.
- Testes de concorrência exercitam a disputa pelo último lugar disponível.

## 6. Criar a interface do site

**Objetivo:** oferecer ao usuário o fluxo descrito sem exigir chamadas manuais à API.

**Instruções:**
- Criar uma tela para escolher uma das linhas disponíveis.
- Exibir os ônibus da linha com estado, horário de saída e ocupação em relação à capacidade.
- Ao selecionar um ônibus, apresentar paradas ordenadas e posição atual.
- Solicitar as paradas de subida e descida, mostrar erros de validação e enviar a solicitação à API.
- Atualizar a lotação exibida após confirmação e informar indisponibilidade quando a capacidade for insuficiente.

**Critérios de aceite:**
- O usuário completa o fluxo de seleção de linha, ônibus e paradas pela interface.
- O site apresenta os dados retornados pela API, sem valores fictícios.
- Erros de validação e de capacidade são mostrados de forma compreensível.
- Testes cobrem o fluxo principal e respostas de erro da API.
