# Securitate

Aplicatia foloseste **Spring Security** cu **token JWT**.
Utilizatorul se logheaza o data si primeste un token. Apoi trimite tokenul la fiecare cerere,
ca serverul sa stie cine este.

## Conturi de test

| Email | Parola | Rol |
|---|---|---|
| profesor@teste.ro | admin1234 | PROFESOR |
| student@teste.ro | client1234 | STUDENT |

Parolele sunt salvate in baza de date criptate (BCrypt), nu in clar.

## Cine ce are voie

| Resursa | Anonim | STUDENT | PROFESOR |
|---|---|---|---|
| Swagger UI (`/swagger-ui.html`) si documentatia (`/v3/api-docs`) | permis | permis | permis |
| `POST /api/auth/login` (login) | permis | permis | permis |
| `GET /api/auth/eu` (cine sunt eu) | interzis | permis | permis |
| `GET /api/teste` (lista testelor) | interzis | permis | permis |
| `GET /api/teste/{id}` (test cu raspunsurile corecte) | interzis | interzis | permis |
| creare / modificare / stergere teste | interzis | interzis | permis |
| creare / modificare / stergere intrebari | interzis | interzis | permis |

## Pasii

1. Login: `POST /api/auth/login` cu email si parola. Raspunsul contine tokenul.
2. La fiecare cerere se trimite header-ul `Authorization: Bearer <token>`.
3. Tokenul e valabil 1 ora. Dupa aceea se face login din nou.
4. Autorul unui test nou este cel logat (luat din token).

## Erori

| Cod | Ce inseamna |
|---|---|
| 401 | nu esti logat: lipseste tokenul, e gresit sau a expirat (sau email/parola gresite la login) |
| 403 | esti logat, dar rolul tau nu are voie |

## De ce asa

- **Token, nu sesiune**: frontend-ul va fi in React, separat de server; tokenul e simplu de trimis.
- **Fara CSRF**: atacul CSRF foloseste cookie-urile trimise automat de browser; noi nu folosim cookie-uri.

## Cum testez

In `backend/teste-api.http`: rulezi intai `LOGIN profesor` (tokenul se salveaza singur),
apoi celelalte cereri. Blocul `STUDENT` arata ce primeste un student (200 la lista, 403 in rest).
