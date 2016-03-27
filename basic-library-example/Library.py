"""
A basic program to emulate a library through Command Line console.

This program supports the following functions:
    load books/movies data from disk;
    Check in a book/movie
    Check out a book/movie
    Add a new book/movie
    Display all books
    Display all movies
    Query books through keywords
    Query movies through keywords
"""
def load_collections():
    """
    Loads data for both books and movies,
    returning a dictionary with two keys, 'books' and 'movies',
    one for each subset of the collection.
    """
    # Load the two collections.
    book_collection, max_book_id = load_collection("books.csv")
    movie_collection, max_movie_id = load_collection("movies.csv")

    # Check for error.
    if (book_collection is None) or (movie_collection is None):
        return None, None

    # Return the composite dictionary.
    return {"books": book_collection, "movies": movie_collection}, max(max_book_id, max_movie_id)


def load_collection(file_name):
    """
    Loads a single collection and returns the data as a list.
    Upon error, None is returned.
    """
    max_id = -1
    try:
        # Create an empty collection.
        collection = []

        # Open the file and read the field names
        collection_file = open(file_name, "r")
        field_names = collection_file.readline().rstrip().split(",")

        # Read the remaining lines, splitting on commas,
        # and creating dictionaries (one for each item)
        for item in collection_file:
            field_values = item.rstrip().split(",")
            collection_item = {}
            for index in range(len(field_values)):
                if (field_names[index] == "Available") or (field_names[index] == "Copies") or (field_names[index] == "ID"):
                    collection_item[field_names[index]] = int(field_values[index])
                else:
                    collection_item[field_names[index]] = field_values[index]
            # Add the full item to the collection.
            collection.append(collection_item)
            # Update the max ID value
            max_id = max(max_id, collection_item["ID"])

        # Close the file now that we are done reading all of the lines.
        collection_file.close()

    # Catch IO Errors, with the File Not Found error the primary possible problem to detect.
    except FileNotFoundError:
        print("File not found when attempting to read", file_name)
        return None
    except IOError:
        print("Error in data file when reading", file_name)
        collection_file.close()
        return None

    # Return the collection.
    return collection, max_id

def prompt_user_with_menu():
    """
    Display the menu of commands and get user's selection.
    Returns a string with the user's requested command.
    No validation is performed.
    """
    print("\n\n********** Welcome to the Collection Manager. **********")
    print("COMMAND    FUNCTION")
    print("  ci         Check in an item")
    print("  co         Check out an item")
    print("  ab         Add a new book")
    print("  am         Add a new movie")
    print("  db         Display books")
    print("  dm         Display movies")
    print("  qb         Query for books")
    print("  qm         Query for movies")
    print("  x          Exit")
    return input("Please enter a command to proceed: ")

def main():
    """
    This is the main program function. This function should
    (1) Load the data and (2) Manage the main program loop that
    lets the user perform the various operations (ci, co, qb, etc.)
    """
    # Load the collections, and check for an error.
    library_collections, max_existing_id = load_collections()
    if library_collections is None:
        print("The collections could not be loaded. Exiting.")
        return
    print("The collections have loaded successfully.")

    # Display the menu and get the operation code entered by the user.  We perform this continuously until the
    # user enters "x" to exit the program.  Calls the appropriate function that corresponds to the requested operation.
    operation = prompt_user_with_menu()
    while operation != "x":
        ###############################################################################################################
        ###############################################################################################################
        # HINTS HINTS HINTS!!! READ THE FOLLOWING SECTION OF COMMENTS!
        ###############################################################################################################
        ###############################################################################################################
        #
        # The commented-out code below gives you a some good hints about how to structure your code.
        #
        # FOR BASIC REQUIREMENTS:
        #
        # Notice that each operation is supported by a function.  That is good design, and you should use this approach.
        # Moreover, you will want to define even MORE functions to help break down these top-level user operations into
        # even smaller chunks that are easier to code.
        #
        # FOR ADVANCED REQUIREMENTS:
        #
        # Notice the "max_existing_id" variable?  When adding a new book or movie to the collection, you'll need to
        # assign the new item a unique ID number.  This variable is included to make that easier for you to achieve.
        # Remember, if you assign a new ID to a new item, be sure to keep "max_existing_id" up to date!
        #
        # Have questions? Ask on Piazza!
        #
        ###############################################################################################################
        ###############################################################################################################
        # My Code Here:
        if (operation == "ci"):
            check_in(library_collections)
        elif (operation == "co"):
            check_out(library_collections)
        elif (operation == "ab"):
            max_existing_id = add_book(library_collections, max_existing_id)
        elif (operation == "am"):
            max_existing_id = add_movie(library_collections, max_existing_id)
        elif (operation == "db"):
            display_collection(library_collections["books"], True)
        elif (operation == "dm"):
            display_collection(library_collections["movies"], False)
        elif (operation == "qb"):
            query_collection(library_collections["books"], True)
        elif (operation == "qm"):
            query_collection(library_collections["movies"], False)
        else:
            print("Unknown command.  Please try again.")

        operation = prompt_user_with_menu()

def check_in(library_collections):
    """
    Check in an item by pressing 'ci' and prompt to enter an ID number.

    This function will check to see if the item has been checked out;
    if so, increase availability number and notify user;
    else the user should be shown error msg.
    """
    item_id = int(input('Enter the ID for the item you wish to check in: '))
    library = library_collections['books'] + library_collections['movies']

    for idx, item in enumerate(library):
        if item['ID'] != item_id:
            continue
        elif item['Available'] < item['Copies']:
            library[idx]['Available'] += 1
            print('Your check in with ID {0} has succeeded'.format(item['ID']))
            display_collection([library[idx]], _decide_book_or_movie(library, idx))
            return
        elif item['Available'] == item['Copies']:
            print('All copies are already available, so this item can not be checked in.')
            return
    print('Your input ID {0} is invalid!'.format(item_id))

def check_out(library_collections):
    """
    Check out an item by pressing 'co' and prompt to enter an ID.

    Needs to check if the requested item is available;
    if so, decrease the availability number and notify user;
    else show error msg.
    """
    item_id = int(input('Enter the ID for the item you wish to check out: '))
    library = library_collections['books'] + library_collections['movies']

    for idx, item in enumerate(library):
        if item['ID'] != item_id:
            continue
        elif item['Available'] > 0:
            library[idx]['Available'] -= 1
            print('Your check out with ID {0} has succeeded.'.format(item['ID']))
            display_collection([library[idx]], _decide_book_or_movie(library, idx))
            return
        elif item['Available'] == 0:
            print('No copies of the item are available for check out.')
            return
    print('Your input ID {0} is invalid!'.format(item_id))

def _decide_book_or_movie(library, idx):
    """
    Decide the type of `library[idx]` as book or movie,
    by the fact that books have authors while movies have directors
    """
    return 'Author' in library[idx]

def query_collection(collection, flag_of_books_query):
    """
    Query either books or movies collection by a boolean flag.

    Search lower-case query string into the specific fields of book/movie.
    Once the field of an item has the query as substring, add it into
    result list. Then display all matched books/movies.
    """
    query_str = input('Enter a query string to use for the search: ')
    if len(query_str) == 0:
        print('Invalid query string.')
        return

    res = []
    query_str = query_str.lower()
    for idx, item in enumerate(collection):
        if (flag_of_books_query and (
                query_str in item['Title'].lower() or
                query_str in item['Author'].lower() or
                query_str in item['Publisher'].lower())):
            res.append(item)
        elif (not flag_of_books_query and (
                query_str in item['Title'].lower() or
                query_str in item['Director'].lower() or
                query_str in item['Genre'].lower())):
            res.append(item)
    display_collection(res, flag_of_books_query)

def add_book(library_collections, max_existing_id):
    """
    Add a new book from user's input.

    @return max_existing_id
    """
    print('Please enter the following attributes for the new book.')
    collection_item = {}
    # Set unique id for the item and update max id
    max_existing_id += 1
    collection_item['ID'] = max_existing_id
    collection_item['Title'] = input('Title: ')
    collection_item['Author'] = input('Author: ')
    collection_item['Publisher'] = input('Publisher: ')
    collection_item['Pages'] = int(input('Pages: '))
    collection_item['Year'] = int(input('Year: '))
    collection_item['Copies'] = int(input('Copies: '))
    # Initialize availability of books by copies
    collection_item['Available'] = collection_item['Copies']

    print('You have entered the following data:')
    display_collection([collection_item], True)

    # Prompt to ask for permission to add this item
    cancel = input("Press enter to add this item to the collection.  Enter 'x' to cancel.")
    if(not cancel):
        library_collections['books'].append(collection_item)
        return max_existing_id
    elif(cancel == 'x'):
        return max_existing_id - 1

def add_movie(library_collections, max_existing_id):
    print('Please enter the following attributes for the new movie.')
    collection_item = {}
    # Set unique id for the item and update max id
    max_existing_id += 1
    collection_item['ID'] = max_existing_id
    collection_item['Title'] = input('Title: ')
    collection_item['Director'] = input('Director: ')
    collection_item['Length'] = int(input('Length: '))
    collection_item['Genre'] = input('Genre: ')
    collection_item['Year'] = int(input('Year: '))
    collection_item['Copies'] = int(input('Copies: '))
    # Initialize availability of books by copies
    collection_item['Available'] = collection_item['Copies']

    print('You have entered the following data:')
    display_collection([collection_item], False)

    # Prompt to ask for permission to add this item
    cancel = input("Press enter to add this item to the collection.  Enter 'x' to cancel.")
    if(not cancel):
        library_collections['movies'].append(collection_item)
        return max_existing_id
    elif(cancel == 'x'):
        return max_existing_id - 1

def display_collection(items, flag_of_books_query):
    """
    Display all items in the given list, either books or movies;

    @param	items	a list of book/movie dictionary items
    @param	flag_of_books_query	a flag to mark if is book list or not
    """
    if len(items) == 0 and flag_of_books_query:
        print('No related books are found.')
    elif len(items) == 0 and not flag_of_books_query:
        print('No related movies are found.')
    else:
        for target in items:
            print('ID: {0}'.format(target['ID']))
            print('Title: {0}'.format(target['Title']))
            print('Year: {0}'.format(target['Year']))
            print('Copies: {0}'.format(target['Copies']))
            print('Available: {0}'.format(target['Available']))
            if(flag_of_books_query):
                print('Author: {0}'.format(target['Author']))
                print('Publisher: {0}'.format(target['Publisher']))
                print('Pages: {0}'.format(target['Pages']))
            else:
                print('Director: {0}'.format(target['Director']))
                print('Length: {0}'.format(target['Length']))
                print('Genre: {0}'.format(target['Genre']))
            print('')

# Start the program!
main()
