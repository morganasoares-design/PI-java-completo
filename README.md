# OMNIRoute — AV2 Java Desktop
Projeto didático em Java Swing, MVC + DAO e SQLite.

## Executar
1. Instale JDK 17+ e Apache Maven.
2. Abra esta pasta no NetBeans como projeto Maven.
3. Aguarde o Maven baixar SQLite JDBC.
4. Execute `br.com.omniroute.Main`.
5. O banco `omniroute.db` e as tabelas são criados automaticamente.

Login inicial: `admin@omniroute.com` / `123456`

## Funcionalidades
Login, cadastro de encomendas, listagem em JTable, edição, exclusão com confirmação,
criação de rotas associando encomendas e exportação TXT/CSV.

## Pacotes
`model`, `model.dao`, `view`, `controller`, `util`.

## Polimorfismo
A interface `Exportador` possui duas implementações: `ExportadorTXT` e `ExportadorCSV`.
A tela escolhe a implementação por meio da referência do tipo `Exportador`.

Observação: OCR, mapa real e cálculo geográfico de rota não estão implementados nesta versão AV2.
A senha inicial é apenas para demonstração acadêmica.
