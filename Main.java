import javax.swing.*;
import java.security.SecureRandom;

public class Main {

    public static int calcolaCriticoSubito(int hpAttuale, boolean boss) {
        if (boss) return hpAttuale - 40;
        return hpAttuale - 30;
    }

    public static int combattimento(Player p, String nomeM, int hpM, int difM, int bonusM, SecureRandom r, boolean isBoss) {
        if (isBoss) {
            JOptionPane.showMessageDialog(null, "ATTENZIONE: SCONTRO FINALE CON " + nomeM, "Boss Alert!", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Inizia scontro con: " + nomeM + "\nHP: " + hpM + "\nDifesa: " + difM, "Inizio Combattimento", JOptionPane.INFORMATION_MESSAGE);
        }

        while (p.hp > 0 && hpM > 0) {
            // Il popup blocca il gioco finché lo studente non preme OK, simulando l'invio del terminale
            JOptionPane.showMessageDialog(null, "Clicca OK per lanciare il dado!", "Tuo Turno", JOptionPane.QUESTION_MESSAGE);

            int dadoP = r.nextInt(20) + 1;
            String resGiocatore = "Hai lanciato un: " + dadoP + "\n";

            int dannoP = 0;
            if (dadoP == 1) {
                resGiocatore += "MISS !";
            } else if (dadoP == 20) {
                resGiocatore += "CRITICAL HIT !";
                dannoP = 40;
            } else {
                if (p.classe.equals("guerriero")) {
                    dannoP = dadoP + (p.forza / 2);
                } else if (p.classe.equals("mago")) {
                    dannoP = dadoP + (p.intelligenza / 2);
                } else {
                    dannoP = dadoP + (p.agilita / 2);
                }
                dannoP = dannoP - difM;
                if (dannoP < 1) dannoP = 1;
            }

            hpM -= dannoP;
            resGiocatore += "\nInfliggi " + dannoP + " danni. HP mostro rimasti: " + hpM;
            JOptionPane.showMessageDialog(null, resGiocatore, "Esito Tuo Lancio", JOptionPane.INFORMATION_MESSAGE);

            if (hpM > 0) {
                JOptionPane.showMessageDialog(null, "Il nemico si prepara ad attaccare...", "Turno Nemico", JOptionPane.INFORMATION_MESSAGE);

                int dadoN = r.nextInt(20) + 1;
                String resNemico = "Il nemico attacca col dadi: " + dadoN + "\n";

                if (dadoN == 1) {
                    resNemico += "Il nemico ti manca!";
                } else if (dadoN == 20) {
                    resNemico += "Il nemico ti fa un critico!";
                    p.hp = calcolaCriticoSubito(p.hp, isBoss);
                } else {
                    int subisce = (dadoN / 2) + bonusM;
                    p.hp -= subisce;
                    resNemico += "Subisci " + subisce + " danni.";
                }

                resNemico += "\nI tuoi HP attuali: " + p.hp;
                JOptionPane.showMessageDialog(null, resNemico, "Esito Attacco Nemico", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return hpM;
    }

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();


        String nome = JOptionPane.showInputDialog(null, "Inserisci il tuo nome:", "Creazione Personaggio", JOptionPane.QUESTION_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) nome = "Eroe";

        String[] opzioniClasse = {"Guerriero", "Mago", "Ladro"};
        int sceltaClasse = JOptionPane.showOptionDialog(null, "Scegli la tua classe:", "Selezione Classe",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioniClasse, opzioniClasse[0]);

        String classeScelta = "guerriero";
        if (sceltaClasse == 1) classeScelta = "mago";
        if (sceltaClasse == 2) classeScelta = "ladro";

        Player p1 = new Player();
        p1.nome = nome;
        p1.classe = classeScelta;

        if (p1.classe.equals("guerriero")) {
            p1.hp = 120; p1.mp = 10; p1.forza = 15; p1.intelligenza = 5;
            p1.destrezza = 8; p1.fortuna = 5; p1.agilita = 5; p1.difesa = 12; p1.arcano = 2;
        } else if (p1.classe.equals("mago")) {
            p1.hp = 80; p1.mp = 50; p1.forza = 4; p1.intelligenza = 18;
            p1.destrezza = 6; p1.fortuna = 7; p1.agilita = 8; p1.difesa = 5; p1.arcano = 15;
        } else {
            p1.hp = 100; p1.mp = 20; p1.forza = 9; p1.intelligenza = 9;
            p1.destrezza = 15; p1.fortuna = 12; p1.agilita = 14; p1.difesa = 7; p1.arcano = 4;
        }

        JOptionPane.showMessageDialog(null, "Trovi un baule abbandonato...", "Evento", JOptionPane.INFORMATION_MESSAGE);

        int arma = random.nextInt(3) + 1;
        if (arma == 1) {
            String msg = "Hai trovato una spada pesante.";
            if (p1.classe.equals("guerriero")) {
                p1.forza += 5;
                msg += "\nBonus classe applicato. Nuova forza: " + p1.forza;
            }
            JOptionPane.showMessageDialog(null, msg, "Baule", JOptionPane.INFORMATION_MESSAGE);
        } else if (arma == 2) {
            String msg = "Trovato bastone antico.";
            if (p1.classe.equals("mago")) {
                p1.intelligenza += 5;
                msg += "\nBonus classe applicato. Nuova intelligenza: " + p1.intelligenza;
            }
            JOptionPane.showMessageDialog(null, msg, "Baule", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String msg = "Trovati pugnali affilati.";
            if (p1.classe.equals("ladro")) {
                p1.agilita += 5;
                msg += "\nBonus classe applicato. Nuova agilità: " + p1.agilita;
            }
            JOptionPane.showMessageDialog(null, msg, "Baule", JOptionPane.INFORMATION_MESSAGE);
        }

        String[] opzioniMondo = {"1) Foresta Maledetta", "2) Caverne di Fuoco"};
        int sceltaMondo = JOptionPane.showOptionDialog(null, "Seleziona il mondo in cui vuoi giocare:", "Scelta Mondo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioniMondo, opzioniMondo[0]);

        String nemico = "";
        String boss = "";
        int hpNemico = 0, difNemico = 0, bonusDannoNemico = 0;
        int hpBoss = 0, difBoss = 0, bonusDannoBoss = 0;

        if (sceltaMondo == 0) {
            JOptionPane.showMessageDialog(null, "Entri nella Foresta Maledetta infestata da ragni, intorno a te vedi solo ragnatele. Stai attento, i ragni sono rapidi e hanno un veleno letale.", "Lore Mondo", JOptionPane.INFORMATION_MESSAGE);
            nemico = "Ragno di Bosco Atro";
            hpNemico = 35; difNemico = 2; bonusDannoNemico = 1;
            boss = "ENORME RAGNO DI CAVERNA (BOSS)";
            hpBoss = 110; difBoss = 5; bonusDannoBoss = 4;
        } else {
            JOptionPane.showMessageDialog(null, "Scendi nelle Caverne di Fuoco, ci sono i draghi che sono pronti ad attaccarti con fiumi di lava. Nasconditi al più presto, non farti prendere!", "Lore Mondo", JOptionPane.INFORMATION_MESSAGE);
            nemico = "Piccolo Drago di Cenere";
            hpNemico = 55; difNemico = 8; bonusDannoNemico = 3;
            boss = "DRAGO DI LAVA (BOSS)";
            hpBoss = 140; difBoss = 12; bonusDannoBoss = 7;
        }

        
        for (int i = 1; i <= 5; i++) {
            if (p1.hp <= 0) break;

            JOptionPane.showMessageDialog(null, "Fase " + i + " di 5", "Progresso", JOptionPane.INFORMATION_MESSAGE);

            combattimento(p1, nemico, hpNemico, difNemico, bonusDannoNemico, random, false);

            if (p1.hp > 0) {
                JOptionPane.showMessageDialog(null, "Nemico eliminato.", "Vittoria Fase", JOptionPane.INFORMATION_MESSAGE);

                if (random.nextInt(10) + 1 <= 4) {
                    p1.hp += 25;
                    JOptionPane.showMessageDialog(null, "Trovi una pozione medica! Recuperi 25 HP. HP attuali: " + p1.hp, "Oggetto Trovato", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (p1.hp > 0) {
            combattimento(p1, boss, hpBoss, difBoss, bonusDannoBoss, random, true);
        }

        if (p1.hp <= 0) {
            JOptionPane.showMessageDialog(null, "Sei stato sconfitto. Riprova, sarai più fortunato.", "Game Over", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Missione completata con successo! Hai vinto, complimenti!", "Vittoria!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
