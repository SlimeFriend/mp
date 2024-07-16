CREATE TABLE LIKES(board_no number(38),
                  like_no number(3),
                  hate_no number(3),
                  c_user varchar2(21));
              





CREATE SEQUENCE jsp.music_board_no
       START WITH 1000 
       MAXVALUE 1999;
       
       
       
       
CREATE SEQUENCE jsp.beauty_board_no
       START WITH 2000
        MAXVALUE 2999;
        

                   

DROP TABLE LIKES;

CREATE SEQUENCE jsp.likes_no
       INCREMENT BY 1
       START WITH 1
       MAXVALUE 999;
       