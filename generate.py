import os
import random

def write_file(file, content):
	f = open(file, "w")
	f.write(content)
	f.close()

def gen_genre_vector(g, count):
	r = ""
	for i in range(count):
		r += "1, " if g == i else "0, "
	return r[:-2]

def gen_arff(name, genres, songs):
	genre_count = len(genres)
	output = "@RELATION '" + name + "'\n"
	output += "%rows=" + str(len(songs)) + "\n"
	output += "%colums=" + str(1 + genre_count) + "\n"
	output += "\n"
	output += "@ATTRIBUTE Id NUMERIC\n"
	output += "@ATTRIBUTE Path STRING\n"
	output += "@ATTRIBUTE Unit {milliseconds,samples}\n"
	output += "@ATTRIBUTE Start NUMERIC\n"
	output += "@ATTRIBUTE End NUMERIC\n"
	for id, genre in genres:
		output += "@ATTRIBUTE '" + str(genre) + "' Numeric\n"
	output += "\n"
	output += "\n"
	output += "@DATA\n"
	for index, (id, song_file) in enumerate(songs):
		output += str(index) + ", '" + song_file + "' milliseconds, 0, -1, " + gen_genre_vector(id, genre_count) + "\n"
	return output

root = "/scratch/Musikinformatik/Genres-Datensatz"

genres = []
songs = []

for id, genre in enumerate(os.listdir(root)):
	genres.append((id, genre))
	for file in os.listdir(os.path.join(root, genre)):
		songs.append((id, os.path.join(root, genre, file)))



arff = gen_arff("Data", genres, songs)

write_file("music_data.arff", arff)




