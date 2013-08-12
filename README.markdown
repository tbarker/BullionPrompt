BullionPrompt
======================

A simple API library and command-line tool for using [BullionVault](http://www.bullionvault.com)'s trading API.


Usage
------

From the command-line

    T-Laptop:BullionPrompt thomasbarker$ java -jar dist/bullionprompt.jar 
    Usage: <main class> [options] [command] [command options]
      Options:
      Commands:
        balance   Display account property balances and valuations.
        market    Show the markets prices.
        watch     null
        cancel    Cancel an existing market order.
        orders    Display your (by default only active) orders.  Or a single given order.
        place     Place a live market order.
        deals     Display the last deals on the market.
        tail      Show changes to the markets.
        depth     Show the market pricing depth.
        spot      Check the spot price.
        help      

See http://www.bullionvault.com/help/?xml_api.html for more.


Alternatively...

Fire up your favourite Java IDE and start at com.thomasbarker.bullionprompt.xmlapi.BVSession

    BVSession session = new BVSession();
    session.login( "testuser", "apassword" );
    List<Order> orders = session.orders();


Build
-----

You will require Ant and [Ivy](http://ant.apache.org/ivy).

First resolve the dependencies

    ant resolve

Then build the Jar

    ant dist


Disclaimer
----------

This is not an offical BullionVault product.  It exists to teach me JAXB and to try out [Lombok](http://projectlombok.org/).  Bugs probably remain.  The dictionary teaches us the prices vary and speculation can result in losses.


Future Work
-----------

+ Quote function that predicts an order's execution price
+ Better client-side validation
+ Automatic data refreshes and a listener-style event-change API

