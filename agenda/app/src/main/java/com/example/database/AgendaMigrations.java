package com.example.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.model.TipoTelefone;

public class AgendaMigrations {
    private static final Migration MIGRATION_3_2 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //Criar nova tabela com as informações desejadas
            database.execSQL("CREATE TABLE IF NOT EXISTS 'Aluno_Novo' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'nome' TEXT, " +
                    "'telefone' TEXT, " +
                    "'email' TEXT) ");

            //Copiar dados da tabela antiga para a nova
            database.execSQL("INSERT INTO Aluno_Novo (id, nome, telefone, email)" +
                    "SELECT id, nome, telefone, email FROM Aluno");

            //Remover tabela antiga
            database.execSQL("DROP TABLE Aluno");

            //Renomear a tabela nova com o nome da antiga
            database.execSQL("ALTER TABLE Aluno_Novo RENAME TO Aluno");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE aluno ADD COLUMN dataCadastro INTEGER");
        }
    };
    private static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'Aluno_Novo' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'nome' TEXT, " +
                    "'telefone' TEXT, " +
                    "'email' TEXT, " +
                    "'celular' TEXT) ");

            database.execSQL("INSERT INTO Aluno_Novo (id, nome, telefone, email)" +
                    "SELECT id, nome, telefone, email FROM Aluno");

            database.execSQL("DROP TABLE Aluno");

            database.execSQL("ALTER TABLE Aluno_Novo RENAME TO Aluno");
        }
    };

    private static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'Aluno_Novo' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'nome' TEXT, " +
                    "'email' TEXT) ");

            database.execSQL("INSERT INTO Aluno_Novo (id, nome, email)" +
                    "SELECT id, nome, email FROM Aluno");

            database.execSQL("CREATE TABLE IF NOT EXISTS 'Telefone' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "'numero' TEXT, " +
                    "'tipo' TEXT, " +
                    "'alunoId' INTEGER NOT NULL) ");

            database.execSQL("INSERT INTO Telefone (numero, alunoId)" +
                    "SELECT telefone, id FROM Aluno");

            database.execSQL("UPDATE Telefone SET tipo = ?",
                    new TipoTelefone[] {TipoTelefone.FIXO});

            database.execSQL("DROP TABLE Aluno");

            database.execSQL("ALTER TABLE Aluno_Novo RENAME TO Aluno");
        }
    };

    public static final Migration[] TODAS_MIGRATIONS = {
            MIGRATION_3_2,
            MIGRATION_3_4,
            MIGRATION_4_5,
            MIGRATION_5_6,
            MIGRATION_6_7
    };
}
