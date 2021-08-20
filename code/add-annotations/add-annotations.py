import psycopg2 as psy
import sys, getopt

def add_labels(host, port, db, user, pwd):
    conn = psy.connect(host=host, port=port, database=db, user=user, password=pwd)
#onn = psy.connect(host="localhost", port= 5432, database="dbconsent", user="postgres", password="password")

    cur=conn.cursor();

    cur.execute("""SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'""")

    relations = cur.fetchall()
    print(relations)

    cur.execute('CREATE SEQUENCE universal_sequence NO MAXVALUE NO CYCLE;')
    conn.commit()

    for r in relations:
        cur.execute("ALTER TABLE " + r[0] + " ADD COLUMN id integer NOT NULL DEFAULT nextval('universal_sequence');")
        conn.commit()

    cur.close()
    conn.close()

def parse_args(argv):
    hst = ""
    prt = ""
    db = ""
    usr = ""
    pwd = ""
    try:
        opts, args = getopt.getopt(argv, "h:p:d:u:w:")
    except getopt.GetoptError:
	sys.exit("Please supply appropriate arguments")
    for opt, arg in opts:
        if opt == '-h':
	    hst = arg
	elif opt == '-p':
	    prt = arg
	elif opt == '-d':
	    db = arg
	elif opt == '-u':
	    usr = arg
    	elif opt == '-w':
	    pwd = arg
    add_labels(hst, prt, db, usr, pwd)

def parse_file(configfile):
    config = open(configfile, 'r')
    line = config.readline()
    args = line.rstrip().split(",")
    config.close()
    add_labels(*args)

if len(sys.argv) == 1:
    add_labels("localhost", 5432, "dbconsent", "postgres", "password")
elif len(sys.argv) == 2:
    parse_file(sys.argv[1])
else:
    parse_args(sys.argv[1:])
