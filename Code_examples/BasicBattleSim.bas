
Type Terrain
	Resolution as Integer 			/'Opløsningen af højde-værdierne (Jpg og png er 256 resolution)'/
	Map()() as Integer
	PixelLength as Float 			'Den aktuelle længde per pixel i meter
	MaxRealHeight as Integer 		'Den maksimale højde i meter for en pixel = Resolution
	action CreateTerrainFromImg(path as String)
		'Læser filen og initialiserer Map
	End action
End Type

Type Coord
End Type

Dcl terrain as Terrain //Standard library der genererer 2D-array elevation-matrix
terrain = terrain.CreateTerrainFromImg("Heightmap.png")

Type Unit
	ID as Integer
	Name as String
	Rank as Integer
	Position as Coord
	action Move (x as Integer, y as Integer)
		' ... Move funktionaltiet
	End Move
End Type

Dcl soldier as Unit
soldier.ID = 0
soldier.Name = "John"
soldier.Rank = 2
soldier.Position = (10, 20)

Dcl soldier2 as Unit
soldier2(ID, Name, Rank, Position) = 1, "Gert", 3, (11, 20)

Dcl soldier3 as Unit(ID, Name, Rank, Position) = 2, "Charles", 3, (12, 22)

soldier2.MoveTo(11, 21) 'Flytter soldier2 til koordinatet (11, 21)

Dcl platoon as Platoon = {soldier, soldier2, soldier3}

If soldier.Position.Height > 100 Then
	platoon.Move(30, N) 'Flytter platoon 30 units nord
End If

Dcl base as Coord = (50, 50)

If platoon.AverageHeight > 100 Then
	platoon.Move(30, NW)
ElseIf platoon > 90 Then
	platoon.MoveTo(base)
End If
