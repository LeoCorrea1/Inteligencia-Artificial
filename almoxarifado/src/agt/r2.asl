viagens(10).
guarda(med).

!start.

+!start : true 
    <-
        ?viagens(Qtd);
        ?guarda(peca); 
        .print("Sou responsavel pelas peças " , Peca " e tenho " , Qtd " viagens).
