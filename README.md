litle-sdk-doc-gen
=================

This repository takes the XML Reference Guide and generates api-level documentation and examples for our sdks.  It is intended that this be a standalone application that can be called during the build process.

For example, taking Chapter 4's documentation of the element accNum, it should be converted into:

/**
* The accNum field is a required member of the:
* @see EcheckType
* @see NewAccountInfo
* @see OriginalAccountInfo
* fields defining the account number for the eCheck account
* <p>
* <b>Note: Although the schema does not specify a minimum length for the accNum field,
* the number must be greater than or equal to 4 characters for the transaction to succeed.</b>
* Min Length: 4
* Max Length: 17
*
* @author sdksupport@litle.com
* @version: 8.17
* @since 8.10
*/


And then that code snippet be added to ECheckType.java, NewAccountInfo.java and OriginalAccountInfo.java in the java sdk directly above the methods setAccNum and getAccNum
