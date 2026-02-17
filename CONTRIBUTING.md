# Guia de Contribuição

Este documento estabelece os padrões de branch, commit e Pull Request para este projeto, garantindo consistência e rastreabilidade completa de todas as alterações realizadas.

---

## 1. GitHub Projects: Drafts, Tasks e Issues

O **GitHub Projects** funciona como nosso taskboard centralizado, oferecendo visibilidade total sobre o progresso do desenvolvimento.

### Fluxo Recomendado

O ciclo de vida de uma tarefa segue esta sequência:

1. **Criar Draft** — Inicie no GitHub Projects criando um draft para descrever a ideia ou requisito da tarefa.

2. **Converter para Issue** — Quando a tarefa estiver pronta para desenvolvimento, converta o draft para uma **Issue** no repositório correspondente.

3. **Associar Pull Request** — Após criar a branch e realizar o push do código, vincule a Issue à sua Pull Request usando referências automáticas (ex: `Closes #123`) ou a opção de vinculação manual no GitHub.

Esse fluxo garante rastreabilidade completa de cada tarefa: **Draft → Issue → PR → Merge**.

---

## 2. Padrão de Branch

As branches devem seguir a nomenclatura:

```
tipo/DescricaoEmCamelCase
```

Onde:

- **tipo**: Define o propósito da branch ([ver lista oficial](#tipos-de-commit))
- **DescricaoEmCamelCase**: Resumo conciso da tarefa em inglês ou português

**Exemplos práticos:**

```
feat/AtualizaContributingMd
fix/CorrecoesNoPipeline
refactor/OptimizaConsultasBancoDados
```

---

## 3. Padrão de Commit

Os commits devem seguir estas diretrizes:

- **Primeiro commit da branch**: use o **nome da task** da Issue correspondente
- **Commits subsequentes**: descreva de forma clara e concisa o que foi adicionado ou alterado

Tanto português quanto inglês são aceitos, desde que o conteúdo seja legível e objetivo.

**Exemplos:**

```bash
# Primeiro commit
git commit -m "Atualiza Contributing.md"

# Commits subsequentes
git commit -m "Adiciona seção de GitHub Projects"
git commit -m "Corrige formatação da seção de PR"
git commit -m "Melhora exemplos de branches"
```

### Tipos de Commit

- **feat** — Nova funcionalidade adicionada
- **fix** — Correção de bug ou problema identificado
- **docs** — Atualização ou adição de documentação
- **refactor** — Refatoração ou melhoria interna sem alterar comportamento
- **chore** — Tarefas de manutenção técnica (scripts, configuração, build)
- **test** — Adição ou atualização de testes

---

## 4. Padrão de Título de Pull Request

O título da PR deve seguir o formato:

```
TIPO: Descrição da branch em palavras
```

O tipo deve estar em maiúsculas e corresponder aos tipos de commit listados acima.

**Exemplos:**

```
FEAT: Adiciona nova funcionalidade
FIX: Correcoes no Pipeline
REFACTOR: Otimiza consultas do banco de dados
DOCS: Atualiza CONTRIBUTING.md
```

---

## 5. Descrição da Pull Request

A descrição deve ser clara, concisa e direcionada para facilitar a revisão do código:

- Resuma as alterações realizadas de forma objetiva
- Explique o **por quê** das mudanças quando relevante
- Referencie Issues relacionadas usando `Closes #123` ou similar
- Use português ou inglês de forma consistente

Você pode contar com assistência de IA para refinamento da redação, se necessário. O importante é garantir clareza e profissionalismo.

**Exemplo de descrição:**

```
Adiciona seção detalhada sobre o fluxo Draft → Issue → PR no GitHub Projects.
Melhora exemplos de branches e commits para maior clareza.
Reformula estrutura geral do documento para melhor legibilidade.

Closes #45
```

---

## 6. Pipeline e Aprovação

Toda Pull Request deve ser **aprovada por pelo menos um membro da organização** antes do merge.

---

## 7. Merge e Publicação

Após a aprovação de um revisor:

1. Realize o merge da PR no branch principal
2. A branch deve ser deletada
3. Proceda para a criação de uma release

---

## 8. Padrão de Release

Releases devem ser criadas com versionamento semântico seguindo o padrão **vX.Y.Z**:

- **X** — Versão major (mudanças significativas)
- **Y** — Versão minor (novas funcionalidades compatíveis)
- **Z** — Versão patch (correções e melhorias)

**Processo de release:**

1. Crie uma nova tag com a versão correspondente (ex: `v1.0.0` para a primeira release)
2. Use o título da release idêntico à tag (ex: `v1.0.0`)
3. Utilize a opção **"Generate Release Notes"** para automatizar a geração do changelog

**Exemplo:**

```
Tag: v1.2.3
Título: v1.2.3
```

---

## Conclusão

Seguir estes padrões garante que o projeto mantenha histórico limpo, commits significativos e rastreabilidade completa de todas as contribuições. Obrigado por seguir estas diretrizes!
