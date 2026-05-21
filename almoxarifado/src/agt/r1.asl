viagens(5).
guarda(peq).

!start.

+!start : true
    <-
        ?viagens(Qtd);
        ?guarda(Peca);
        .print("Sou responsavel pelas peças ", Peca,
               " e tenho ", Qtd, " viagens").

+peca(Peca) : viagens(Qtd) & Qtd > 0 & guarda(Peca)
    <-
        .print("Percebi uma peça ", Peca,
               " e vou guardá-la");
        +guardada(Peca).

+peca(Peca) : guarda(Peca) & viagens(Qtd) & Qtd == 0
    <-
        .print("Percebi uma peça ", Peca,
               " mas não tenho mais como guardar peças").