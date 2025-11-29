INSERT INTO pessoa (usuario, nome,telefone)
VALUES ('fer', 'Fernando', '123456789');

INSERT INTO pousada (pou_nome, pou_end, pou_bairro, pou_cid, pou_estado, pou_tel, pou_estrelas, pou_vlr_qua1, pou_vlr_qua2, pou_vlr_qua3, pou_obs, pou_site)
VALUES ('Maceió Atlantic Suítes', 'Avenida Álvaro Otacílio, 4065', 'Centro', 'Maceió', 'AL', '2121-5759', 5, 100.00, 80.00, 120.00, 'Com café', 'http://www.maceioatlantic.com.br/');

INSERT INTO quarto (qua_pou, qua_nome, qua_camas, qua_valor_dia) VALUES
(1, 'Standard Simples', 2, 100.00),
(1, 'Standard Duplo', 2, 100.00),
(1, 'Luxo Simples', 2, 120.00),
(1, 'Luxo Duplo', 2, 120.00),
(1, 'Quarto Família', 4, 150.00);