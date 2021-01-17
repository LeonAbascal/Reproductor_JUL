# Reproductor_JUL
Reproductor de música para programación 3

Usuario: UnaiAI

Contraseña: 1234

Con el botón de choose songs añadiremos a la mitad superior de la ventana archivos mp3. Leeremos un directorio el cual recorreremos en busca
de archivos con dicha extensión. Al inciar el programa, leerá el contenido de la carpeta MusicFiles adjunta.

Con el comboBox en el margen izquierdo podremos elegir una playlist entre las que hayamos creado. Esta se mostrará en el panel central 
inferior. Debería poder encontrarse una de prueba.

Para crear playlist nuevas tendremos una ventana disponible al pulsar el botón "Playlist Management" también a la izquierda.
Se pueden crear nuevas playlist con cualquier archivo mp3 en el sistema, pero también hay incluidos varios archivos mp3 junto con el jar
(carpeta MusicFiles) para hacer la prueba.

Para borrar playlists, lo haremos a través del comboBox en la esquina superior derecha, donde se mostrarán las playlist creadas y nos pedirá
confirmación para eliminar la seleccionada.

En esta ventana, una vez elegido el directorio, se muestran todos los archivos mp3 que se pudieron encontrar en el mismo. Al pulsar el
botón "Save Playlist" nos preguntará por el nombre para la nueva playlist. Si no está en blanco, se añadirán playlist y canción a la BDD.

Al hacer click sobre los nombres de las canciones en los paneles centrales inferior y superior, en la margen derecha nos enseñará su 
metadata (título, artista, álbum, track (del álbum), género y año (de publicación). Los mp3 incluidos ya cuentan con tags.

En el panel inferior tendremos varios botones a nuestra disposición, de izquierda a derecha: Statistics, Configuration, Previous, Stop,
Play, Next, Shuffle, Choose Song.

Statistics nos muestra cuánto se está usando el programa y por quién. Nos brinda información como el nº de playlist por usuario,
el nº de veces que se ha abierto el reproductor y el nº de canciones que han sido reproducidas (por todos los usuarios).

En la configuración podremos elegir las opciones de arranque del programa. De momento, la skin de Siwng con la que se inicia.

El botón de play reproducirá la canción que hallamos seleccionado al hacer click sobre ella. Cuando tenemos una playlist activa,
los botones de previous y next reproducirán automáticamente la anterior o siguiente canción. Al llegar al final, volverá al principio.
Ir hacia atrás desde la primera canción hace que vuelva a empezar.

Además, cuando una canción termina, el reproductor pasa automáticamente a la siguiente de la playlist.
