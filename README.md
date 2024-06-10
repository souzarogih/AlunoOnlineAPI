<h2 align="center">Projeto api de gerenciamento academico para disciplina de backend</h2>

#### Repositório de referência

[tec-backend-uniesp-2024-1](https://github.com/kelsonvictr/tec-backend-uniesp-2024-1)

#### Refatoração

##### Procedimentos para uso da api

- Curso: Curso de nível superior ofertado pela instituição.
- Disciplina: Disciplina com assunto que deve ser ministrado no decorrer do curso.


Cadastrar curso
Cadastrar professores
Cadastrar disciplinas
 
Cadastrar alunos
 - Histórico do aluno
 - Histórico academico do aluno
 - Declaração de matricula do aluno

Financeiro

- remover a matricula do aluno da entidade student para entidade matriculad aluno
- acrescentar o campo media na entidade student_enrollment
- destrancar uma matricula
- no historico do aluno deve fazer filtro pela identificação do student ou da matricula gerada no momento da criação do estudante
- campo na tabela finance para dizer o mes referente ao pagamento da mensalidade 