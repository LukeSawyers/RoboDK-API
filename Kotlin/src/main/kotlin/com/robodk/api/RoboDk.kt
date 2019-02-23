package com.robodk.api

import com.robodk.api.collision.CollisionItem
import com.robodk.api.collision.CollisionPair
import com.robodk.api.events.EventType
import com.robodk.api.events.IRoboDkEventSource
import com.robodk.api.model.*
import org.jblas.DoubleMatrix
import java.awt.Color
import java.util.*

interface RoboDk {

    val applicationDir: String

    /**
     * Returns the number of pairs of objects that are currently in a collision state.
     * @returns Number of pairs of objects in a collision state.
     */
    val collisions: Int

    /**
     * Return the list of items that are in a collision state. This call will run a check for collisions if collision
     * checking is not activated (if SetCollisionActive is set to Off).
     * @returns List of items that are in a collision state
     */
    val collisionItems: List<CollisionItem>

    /**
     * Returns the list of pairs of items that are in a collision state. This call will run a check for collisions if
     * collision checking is not activated (if SetCollisionActive is set to Off).
     * @returns
     */
    val collisionPairs: List<CollisionPair>

    /** The vesion of RoboDK as a 4 digit string: Major.Minor.Revision.Build */
    val version: String

    /** The list of open stations in RoboDK */
    val openStations: List<Item>

    /**
     * Gets all the user parameters from the open RoboDK station.
     * The parameters can also be modified by right clicking the station and selecting "shared parameters"
     * User parameters can be added or modified by the user
     * @returns list of param-value pair
     */
    val parameterList: Map<String, String>

    /** Takes a measurement with the C-Track stereocamera. Returns the result. */
    val stereoCameraMeasure: StereoCameraMeasure

    /** The license string (as shown in the RoboDK main window) */
    val license: String

    /** The list of items selected (it can be one or more items) */
    val selectedItems: List<Item>

    /** the active station (project currently visible) */
    var activeStation: Item

    /**
     * The simulation speed. A simulation speed of 5 (default) means that 1 second of simulation
     * time equals to 5 seconds in a real application. The slowest speed ratio allowed is 0.001.
     * Set a large simulation ratio (>100) for fast simulation results.
     */
    var simulationSpeed: Double

    /**
     * The behavior of the RoboDK API.
     * By default, robodk shows the path simulation for movement instructions (RunMode.Simulate).
     * Setting the run_mode to RunMode.QuickValidate allows performing a quick check to see if the path is feasible.
     * If robot.Connect() is used, RUNMODE_RUN_FROM_PC is selected automatically.
     */
    var runMode: RunMode

    /**
     * Open a new additional RoboDK Link to the same already existing RoboDK instance.
     * @returns New RoboDK Link
     */
    fun newLink(): RoboDk

    /**
     * Start the event communication channel. Use WaitForEvent to wait for a new event or use EventsLoop as an example
     * to implement an event loop.
     * @returns True of event connection to RoboDK could be established.
     */
    fun eventsListen(): IRoboDkEventSource

    /** Close RoboDK Event channel. */
    fun eventsListenClose()

    /** This is a sample function that is executed when a new RoboDK Event occurs. */
    fun sampleRoboDkEvent(evt: EventType, item: Item): Boolean

    /**
     * Run the RoboDK event loop. This is loop blocks until RoboDK finishes execution.
     * Run this loop as a separate thread or create a similar loop to customize the event loop behavior.
     */
    fun eventsLoop(): Boolean

    /** Close RoboDK window and finish RoboDK process. */
    fun closeRoboDK()

    /**
     * Set the state of the RoboDK window
     * @param windowState Window state to be set.
     */
    fun setWindowState(windowState: WindowState = WindowState.NORMAL)

    /**
     * Load a file and attaches it to parent and returns the newly added Item.
     * @param filename
     *     Any file to load, supported by RoboDK.
     *     Supported formats include STL, STEP, IGES, ROBOT, TOOL, RDK,...
     *     It is also possible to load supported robot programs, such as SRC (KUKA),
     *     SCRIPT (Universal Robots), LS (Fanuc), JBI (Motoman), MOD (ABB), PRG (ABB), ...
     *
     * @param parent item to attach the newly added object (optional)
     */
    fun addFile(filename: String, parent: Item? = null): Item

    /**
     * Add a new target that can be reached with a robot.
     * @param name MachineTarget name
     * @param parent Reference frame to attach the target
     * @param robot Robot that will be used to go to target (optional)
     * @returns Newly created target item.
     */
    fun addTarget(name: String, parent: Item? = null, robot: Item? = null): Item

    /**
     * Add a new program to the RoboDK station.
     * Programs can be used to simulate a specific sequence, to generate vendor specific programs (Offline Programming)
     * or to run programs on the robot (Online Programming).
     * @param name Name of the program
     * @param robot Robot that will be used for this program. It is not required to specify the robot if the station
     * has only one robot or mechanism.
     * @returns Newly created Program Item
     */
    fun addProgram(name: String, robot: Item? = null): Item

    /**
     * Add a new empty station.
     * @param name Name of the station
     * @returns Newly created station Item
     */
    fun addStation(name: String): Item

    /**
     * Add a new robot machining project. Machining projects can also be used for 3D printing, following curves and
     * following points.
     * It returns the newly created :class:`.Item` containing the project settings.
     * Tip: Use the macro /RoboDK/Library/Macros/MoveRobotThroughLine.py to see an example that creates a new
     * "curve follow project" given a list of points to follow(Option 4).
     * @param name Name of the project settings
     * @param itemrobot Robot to use for the project settings(optional). It is not required to specify the robot if
     * only one robot or mechanism is available in the RoboDK station.
     */
    fun addMachiningProject(name: String = "Curve follow settings", itemRobot: Item? = null): Item

    /**
     * Display/render the scene: update the display.
     * This function turns default rendering (rendering after any modification of the station unless alwaysRender is
     * set to true).
     * Use Update to update the internal links of the complete station without rendering
     * (when a robot or item has been moved).
     * @param alwaysRender Set to True to update the screen every time the station is modified (default behavior when
     * Render() is not used).
     */
    fun render(alwaysRender: Boolean = false)

    /**
     * Update the screen.
     * This updates the position of all robots and internal links according to previously set values.
     */
    fun update()

    /**
     * Returns an item by its name. If there is no exact match it will return the last closest match.
     * Specify what type of item you are looking for with itemtype.
     * This is useful if 2 items have the same name but different type.
     * @param name name of the item (name of the item shown in the RoboDK station tree)
     * @param itemType type of the item to be retrieved (avoids confusion if there are similar name matches).
     * @returns Returns an item by its name.
     */
    fun getItemByName(name: String, itemType: ItemType = ItemType.ANY): Item?

    /**
     * Returns a list of items (list of names) of all available items in the currently open station in robodk.
     * Optionally, use a filter to return specific items (example: GetItemListNames(itemType = ItemType.Robot))
     * @param itemType Only return items of this type.
     * @returns List of item Names
     */
    fun getItemListNames(itemType: ItemType = ItemType.ANY): List<String>

    /**
     *     Returns a list of items of all available items in the currently open station in robodk.
     *     Optionally, use a filter to return items of a specific type

     * @param itemType Only return items of this type
     * @returns List of Items (optionally filtered by ItemType).
     */
    fun getItemList(itemType: ItemType = ItemType.ANY): List<Item>

    /**
     * Shows a RoboDK popup to select one object from the open station.
     * An item type can be specified to filter desired items. If no type is specified, all items are selectable.
     * The method always returns an Item. Use item.Valid() to check if the selected item is a valid item.
     * E.g. if the user exits the dialog without selecting an item, the method still returns an item object,
     * but item.Valid() will return False.
     * @param message Message to pop up
     * @param itemType optionally filter by ItemType
     * @returns User selected item. Use item.Valid() to check if the item is valid
     */
    fun itemUserPick(message: String = "Pick one item", itemType: ItemType = ItemType.ANY): Item

    /** Shows or raises the RoboDK window. */
    fun showRoboDK()

    /** Fit all */
    fun fitAll()

    /** Hides the RoboDK window. */
    fun hideRoboDK()

    /**
     * Update the RoboDK flags.
     * RoboDK flags allow defining how much access the user has to RoboDK features.
     * Use the flags defined in WindowFlags to set one or more flags.
     * @param flags RoboDkLink Window Flags
     */
    fun setWindowFlags(flags: EnumSet<WindowFlags>)

    /**
     * Show a message in RoboDK (it can be blocking or non blocking in the status bar)
     * @param message Message to display
     * @param popup Set to true to make the message blocking or set to false to make it non blocking
     */
    fun showMessage(message: String, popup: Boolean = true)

    /**
     * Save an item to a file. If no item is provided, the open station is saved.

     * @param filename absolute path to save the file
     * @param itemsave object or station to save. Leave empty to automatically save the current station.
     */
    fun save(filename: String, itemsave: Item? = null)

    /**
     * Adds a shape provided triangle coordinates. Triangles must be provided as a list of vertices.
     * A vertex normal canbe provided optionally.
     * @param trianglePoints
     *     List of vertices grouped by triangles (3xN or 6xN matrix, N must be multiple of 3 because
     *     vertices must be stacked by groups of 3)
     *
     * @param addTo item to attach the newly added geometry (optional). Leave empty to create a new object.
     * @param shapeOverride Set to true to replace any other existing geometry
     * @param color Color of the added shape
     * @returns added object/shape (use item.Valid() to check if item is valid.)
     */
    fun addShape(trianglePoints: DoubleMatrix, addTo: Item? = null, shapeOverride: Boolean = false, color: Color? = null): Item

    /**
     * Adds a curve provided point coordinates.
     * The provided points must be a list of vertices.
     * A vertex normal can be provided optionally.
     * @param curvePoints matrix 3xN or 6xN -> N must be multiple of 3
     * @param referenceObject object to add the curve and/or project the curve to the surface
     * @param addToRef
     *     If True, the curve will be added as part of the object in the RoboDK item tree (a reference
     *     object must be provided)
     *
     * @param projectionType
     *     Type of projection. For example:  ProjectionType.AlongNormalRecalc will project along the
     *     point normal and recalculate the normal vector on the surface projected.
     *
     * @returns added object/curve (use item.Valid() to check if item is valid.)
     */
    fun addCurve(
        curvePoints: DoubleMatrix,
        referenceObject: Item? = null,
        addToRef: Boolean = false,
        projectionType: ProjectionType = ProjectionType.ALONG_NORMAL_RECALC
    ): Item

    /**
     * Adds a list of points to an object. The provided points must be a list of vertices. A vertex normal can be provided optionally.
     * @param points list of points as a matrix (3xN matrix, or 6xN to provide point normals as ijk vectors)
     * @param referenceObject item to attach the newly added geometry (optional)
     * @param addToRef If True, the points will be added as part of the object in the RoboDK item tree (a reference object must be provided)
     * @param projectionType Type of projection.Use the PROJECTION_* flags.
     * @returns added object/shape (0 if failed)
     */
    fun addPoints(
        points: DoubleMatrix,
        referenceObject: Item? = null,
        addToRef: Boolean = false,
        projectionType: ProjectionType = ProjectionType.ALONG_NORMAL_RECALC
    ): Item

    /**
     * Projects a point given its coordinates.
     * The provided points must be a list of [XYZ] coordinates.
     * Optionally, a vertex normal can be provided [XYZijk].
     * @param points matrix 3xN or 6xN -> list of points to project
     * @param objectProject object to project
     * @param projectionType
     *     Type of projection. For example: ProjectionType.AlongNormalRecalc will project along the
     *     point normal and recalculate the normal vector on the surface projected.
     *
     * @returns
     *     It returns the projected points as a list of points (empty matrix if failed).
     *
     */
    fun projectPoints(
        points: DoubleMatrix,
        objectProject: Item,
        projectionType: ProjectionType = ProjectionType.ALONG_NORMAL_RECALC
    ): DoubleMatrix

    /** Closes the current station without suggesting to save. */
    fun closeStation()

    /**
     * Adds a new Frame that can be referenced by a robot.
     * @param name name of the reference frame
     * @param parent parent to attach to (such as the robot base frame)
     * @returns The new reference frame created
     */
    fun addFrame(name: String, parent: Item? = null): Item

    /**
     * Adds a function call in the program output. RoboDK will handle the syntax when the code is generated for a specific
     * robot. If the program exists it will also run the program in simulate mode.
     * @param function Function name with parameters (if any)
     * @returns
     * TODO: Document possible return values.
     */
    fun runProgram(function: String): Int

    /**
     * Adds code to run in the program output.
     * If the program exists it will also run the program in simulate mode.

     * @param code program name or code to generate
     * @param codeIsFunctionCall
     *     Set to True if the provided code corresponds to a function call (same as RunProgram()), if so,
     *     RoboDK will handle the syntax when the code is generated for a specific robot.
     *
     * @returns
     * TODO: Document possible return values.
     */
    fun runCode(code: String, codeIsFunctionCall: Boolean = false): Int

    /**
     * Shows a message or a comment in the output robot program.

     * @param message message or comment to display.
     * @param messageIsComment Set to True to generate a comment in the generated code instead of displaying a message
     * on the teach pendant of the robot.
     */
    fun runMessage(message: String, messageIsComment: Boolean = false)

    /**
     * Check if objectInside is inside the objectParent.

     * @param objectInside
     * @param objectParent
     * @returns Returns true if objectInside is inside the objectParent
     */
    fun isInside(objectInside: Item, objectParent: Item): Boolean

    /**
     * Set collision checking ON or OFF (CollisionCheckOff/CollisionCheckOn) according to the collision map.
     * If collision check is activated it returns the number of pairs of objects that are currently in a collision state.

     * @param collisionCheck collision checking ON or OFF
     * @returns Number of pairs of objects in a collision state
     */
    fun setCollisionActive(collisionCheck: CollisionCheckOptions = CollisionCheckOptions.COLLIION_CHECK_ON): Int

    /** Set all pairs as checking for collisions: */
    fun enableCollisionCheckingForAllItems()

    /** Set all pairs as NOT checking for collisions: */
    fun disableCollisionCheckingForAllItems()

    /**
     * Set collision checking ON or OFF (COLLISION_ON/COLLISION_OFF) for a specific pair of objects.
     * This allows altering the collision map for Collision checking.
     * Specify the link id for robots or moving mechanisms (id 0 is the base).
     * @param collisionCheck Set to COLLISION_ON or COLLISION_OFF
     * @param collisionPair Collision pair (item1, id1, item2, id2) to set
     * @returns Returns true if succeeded. Returns false if setting the pair failed (wrong id was provided)
     */
    fun setCollisionActivePair(collisionCheck: CollisionCheckOptions, collisionPair: CollisionPair): Boolean

    /**
     * Set collision checking ON or OFF (COLLISION_ON/COLLISION_OFF) for a specific list of pairs of objects.
     * This allows altering the collision map for Collision checking.
     * Specify the link id for robots or moving mechanisms (id 0 is the base).
     * @param checkState Set to COLLISION_ON or COLLISION_OFF
     * @param collisionPairs List of collision pairs to set
     * @returns Returns true if succeeded. Returns false if setting the pair failed (wrong id was provided)
     */
    fun setCollisionActivePair(checkState: List<CollisionCheckOptions>, collisionPairs: List<CollisionPair>): Boolean

    /**
     * Check if item1 and item2 collided.
     * @param item1
     * @param item2
     * @param useCollisionMap Turn off collision map check to force collision checking even if it is not set in the
     * collision map.
     * @returns Returns true if item1 collides with item2 false otherwise.
     */
    fun collision(item1: Item, item2: Item, useCollisionMap: Boolean = true): Boolean

    /**
     * Gets a global or a user parameter from the open RoboDK station.
     * The parameters can also be modified by right clicking the station and selecting "shared parameters"
     * Some available parameters:
     * PATH_OPENSTATION = folder path of the current .stn file
     * FILE_OPENSTATION = file path of the current .stn file
     * PATH_DESKTOP = folder path of the user's folder
     * Other parameters can be added or modified by the user

     * @param parameter RoboDK parameter
     * @returns parameter value. Null if parameter does not exist.
     */
    fun getParameter(parameter: String): String

    /**
     * Sets a global parameter from the RoboDK station. If the parameters exists, it will be modified. If not, it will
     * be added to the station.
     * The parameters can also be modified by right clicking the station and selecting "shared parameters"

     * @param parameter RoboDK parameter name
     * @param value parameter value
     */
    fun setParameter(parameter: String, value: String)

    /**
     * Sets a global parameter from the RoboDK station. If the parameters exists, it will be modified. If not, it will
     * be added to the station.
     * The parameters can also be modified by right clicking the station and selecting "shared parameters"

     * @param parameter RoboDK parameter name
     * @param value parameter value (number)
     */
    fun setParameter(parameter: String, value: Double)

    /**
     * Send a special command. These commands are meant to have a specific effect in RoboDK, such as changing a
     * specific setting or provoke specific events.
     * @param cmd Command Name, such as Trace, Threads or Window.
     * @param value Comand value (optional, not all commands require a value)
     */
    fun command(cmd: String, value: String = ""): String

    /**
     * Send a special command. These commands are meant to have a specific effect in RoboDK, such as changing a
     * specific setting or provoke specific events.
     * @param cmd Command Name, such as Trace, Threads or Window.
     * @param value Comand value
     */
    fun command(cmd: String, value: Boolean): String

    /**
     * Send a special command. These commands are meant to have a specific effect in RoboDK, such as changing a
     * specific setting or provoke specific events.
     * @param cmd Command Name, such as Trace, Threads or Window.
     * @param value Comand value
     */
    fun command(cmd: String, value: Double): String

    /**
     * Takes a laser tracker measurement with respect to its own reference frame. If an estimate point is provided, the
     * laser tracker will first move to those coordinates.
     * @param estimate estimate point [x,y,z]
     * @param search If search is True, the tracker will search for a target.
     * @returns Returns the XYZ coordinates of the target (in mm).
     * If the target was not found it retuns a null pointer.
     */
    fun laserTrackerMeasure(estimate: DoubleArray, search: Boolean = false): DoubleArray

    /**
     * Checks the collision between a line and any objects in the station. The line is composed by 2 points.
     * @param p1 Start point [x,y,z] of the line
     * @param p2 End point [x,y,z] of the line
     * @returns Return true if there is a collision false otherwise
     */
    fun collisionLine(p1: DoubleArray, p2: DoubleArray): Boolean

    /**
     * Sets the visibility for a list of items
     * @param itemList list of items
     * @param visibleList list visible flags (Boolean)
     * @param visibleFrames list visible frames (optional, hidden by default)
     */
    fun setVisible(itemList: List<Item>, visibleList: List<Boolean>, visibleFrames: List<Int>? = null)

    /**
     * Sets the color for a list of items
     * @param item_list list of items
     * @param color_list list of colors
     */
    fun setColor(item_list: List<Item>, color_list: List<Color>)

    /**
     * Show a list of objects or a robot link as collided (red) or as not collided (normal color)
     * @param item_list List of items
     * @param collided_list List of collided flags (True=show as collided)
     * @param robot_link_id Robot link ID, when applicable
     */
    fun showAsCollided(item_list: List<Item>, collided_list: List<Boolean>, robot_link_id: List<Int>? = null)

    /**
     * Get Joint positions of all robots defined in the robotItemList.
     * @param robotItemList list of robot items
     * @returns list of robot joints (double x nDOF)
     */
    fun joints(robotItemList: List<Item>): List<DoubleArray>

    /**
     * Sets the current robot joints for a list of robot items and a list of a set of joints.
     * @param robotItemList list of robot items.
     * @param jointsList list of robot joints (double x nDOF).
     */
    fun setJoints(robotItemList: List<Item>, jointsList: List<DoubleArray>)

    /**
     * Calibrate a tool (TCP) given a number of points or calibration joints. Important: If the robot is calibrated,
     * provide joint values to maximize accuracy.
     * @param posesJoints matrix of poses in a given format or a list of joints
     * @param format Euler format. Optionally, use EulerType.JointFormat and provide the robot.
     * @param algorithm type of algorithm (by point, plane, ...)
     * @param robot Robot used for calibration (if using joint values)
     * @returns (TCP as [x, y, z] - calculated TCP, stats[mean, standard deviation, max] - Output error stats summary)
     */
    fun calibrateTool(
        posesJoints: DoubleMatrix,
        format: EulerType = EulerType.EULER_RX_RY_RZ,
        algorithm: TcpCalibrationType = TcpCalibrationType.CALIBRATE_TCP_BY_POINT,
        robot: Item? = null
    ): Pair<DoubleArray, DoubleArray>

    /**
     * Calibrate a Reference Frame given a list of points or joint values. Important: If the robot is calibrated, provide
     * joint values to maximize accuracy.
     * @param joints points as a 3xN matrix or nDOFsxN) - List of points or a list of robot joints
     * @param method type of algorithm(by point, plane, ...)
     * @param useJoints use points or joint values. The robot item must be provided if joint values is used.
     * @param robot
     * @returns TODO: Document return value.
     */
    fun calibrateReference(
        joints: DoubleMatrix,
        method: ReferenceCalibrationType = ReferenceCalibrationType.FRAME_3P_P1_ON_X,
        useJoints: Boolean = false,
        robot: Item? = null
    ): DoubleMatrix


    /**
     * Defines the name of the program when the program is generated. It is also possible to specify the name of the post
     * processor as well as the folder to save the program.
     * This method must be called before any program output is generated (before any robot movement or other instruction).
     * @param progname name of the program
     * @param defaultfolder folder to save the program, leave empty to use the default program folder
     * @param postprocessor
     *     name of the post processor (for a post processor in C:/RoboDK/Posts/Fanuc_post.py it is
     *     possible to provide "Fanuc_post.py" or simply "Fanuc_post")
     *
     * @param robot Robot to link
     * @returns TODO: Document return value.
     */
    fun programStart(
        progName: String,
        defaultFolder: String = "",
        postProcessor: String = "",
        robot: Item? = null
    ): Int

    /**
     * Set the pose of the wold reference frame with respect to the view (camera/screen).
     * @param pose view pose frame.
     */
    fun setViewPose(pose: DoubleMatrix)

    /**
     * Get the pose of the wold reference frame with respect to the view (camera/screen)
     * @param preset Optionally specify a ViewPoseType to retrieve the pose for a specific view
     * @returns Returns the current view pose.
     */
    fun getViewPose(preset: ViewPoseType = ViewPoseType.ACTIVE_VIEW): DoubleMatrix

    /** Sets the nominal robot parameters. */
    fun setRobotParams(robot: Item, dhm: Array<DoubleArray>, poseBase: DoubleMatrix, poseTool: DoubleMatrix): Boolean

    /**
     * Create a new robot or mechanism.
     * @param type Type of the mechanism
     * @param listObj list of object items that build the robot
     * @param param robot parameters in the same order as shown in the RoboDK menu: Utilities-Build Mechanism or robot
     * @param jointsBuild current state of the robot(joint axes) to build the robot
     * @param jointsHome joints for the home position(it can be changed later)
     * @param jointsSenses
     * @param jointsLimLow
     * @param jointsLimHigh
     * @param baseFrame
     * @param tool
     * @param name
     * @param robot existing robot in the station to replace it(optional)
     */
    fun buildMechanism(
        type: Int,
        listObj: List<Item>,
        param: List<Double>,
        jointsBuild: List<Double>,
        jointsHome: List<Double>,
        jointsSenses: List<Double>,
        jointsLimLow: List<Double>,
        jointsLimHigh: List<Double>,
        baseFrame: DoubleMatrix? = null,
        tool: DoubleMatrix? = null,
        name: String = "New robot",
        robot: Item? = null
    ): Item

    /**
     * Open a simulated 2D camera view.
     * Returns a handle pointer that can be used in case more than one simulated view is used.
     * @param item Reference frame or other object to attach the camera
     * @param cameraParameters Camera parameters as a string. Refer to the documentation for more information.
     * @returns Camera pointer/handle. Keep the handle if more than 1 simulated camera is used
     */
    fun cam2DAdd(item: Item, cameraParameters: String = ""): Long

    /**
     * Take a snapshot from a simulated camera view and save it to a file.
     * @param fileSaveImg file path to save.Formats supported include PNG, JPEG, TIFF, ...
     * @param camHandle Camera handle(pointer returned by Cam2DAdd)
     * @returns Return true if image has been saved successfully.
     */
    fun cam2DSnapshot(fileSaveImg: String, camHandle: Long = 0L): Boolean

    /**
     * Closes all camera windows or one specific camera if the camera handle is provided.
     * @param camHandle Camera handle(pointer returned by Cam2DAdd).
     * Leave to 0 to close all simulated views.
     * @returns Returns true if success, false otherwise.
     */
    fun cam2DClose(camHandle: Long = 0): Boolean

    /**
     * Set the parameters of the simulated camera.
     * @param cameraParameters parameter settings according to the parameters supported by Cam2D_Add
     * @param camHandle camera handle (optional)
     * @returns Returns true if success, false otherwise.
     */
    fun cam2DSetParameters(cameraParameters: String, camHandle: Long = 0): Boolean

    /**
     * Show the popup menu to create the ISO9283 path for path accuracy and performance testing
     * @returns IS9283 Program
     */
    fun popup_ISO9283_CubeProgram(robot: Item? = null): Item

    /**
     * Set the interactive mode to define the behavior when navigating and selecting items in RoboDK's 3D view.
     * @param modeType The mode type defines what action occurs when the 3D view is selected (Select object, Pan,
     * Rotate, Zoom, Move Objects, ...)
     * @param defaultRefFlags When a movement is specified, we can provide what motion we allow by default with respect
     * to the coordinate system (set apropriate flags)
     * @param customItems Provide a list of optional items to customize the move behavior for these specific items
     * (important: the length of custom_ref_flags must match)
     * @param customRefFlags Provide a matching list of flags to customize the movement behavior for specific items
     */
    fun setInteractiveMode(
        modeType: InteractiveType = InteractiveType.MOVE_REFERENCES,
        defaultRefFlags: EnumSet<DisplayRefType> = EnumSet.of(DisplayRefType.DEFAULT),
        customItems: List<Item>? = null,
        customRefFlags: EnumSet<DisplayRefType>? = null
    )

    /**
     * Returns the position of the cursor as XYZ coordinates (by default), or the 3D position of a given set of 2D
     * coordinates of the window (x & y coordinates in pixels from the top left corner)
     * The XYZ coordinates returned are given with respect to the RoboDK station(absolute reference).
     * If no coordinates are provided, the current position of the cursor is retrieved.
     * @param xCoord X coordinate in pixels
     * @param yCoord Y coordinate in pixels
     * @param xyzStation XYZ coordinates in mm (absolute coordinates)
     */
    fun getCursorXYZ(xCoord: Int = -1, yCoord: Int = -1, xyzStation: List<Double>? = null): Item

    /**
     * Add a joint movement to a program
     * @param pgm
     * @param targetName
     * @param joints
     * @param robotBase
     * @param robot
     */
    fun addTargetJ(pgm: Item, targetName: String, joints: DoubleArray, robotBase: Item? = null, robot: Item? = null)
}